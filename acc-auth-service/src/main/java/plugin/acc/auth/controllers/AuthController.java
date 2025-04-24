package plugin.acc.auth.controllers;

import java.time.*;
import java.util.*;

import lombok.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

import plugin.acc.auth.*;
import plugin.acc.auth.api.*;
import plugin.acc.auth.dto.*;
import plugin.acc.auth.entity.*;
import plugin.acc.auth.exceptions.*;
import plugin.acc.auth.perository.*;

@Controller
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UsersRepository usersRepository;

    private final CredentialsRepository credentialsRepository;

    private final RefreshTokensRepository refreshRepository;

    private final JwtProperties jwtProps;

    private final JwtProvider jwtProvider;

    private final JwtVerifier jwtVerifier;

    @Override
    public AuthDto login(LoginRequest request) {
        var user = usersRepository.findByLogin(request.getLogin());
        if (user == null) {
            throw new AuthenticationException();
        }

        var salt = credentialsRepository.findSaltByUserId(user.getId());
        if (salt == null || salt.isBlank()) {
            throw new AuthenticationException();
        }

        var hash = BCrypt.hashpw(request.getPassword(), salt);
        var id = credentialsRepository.findId(user.getId(), hash);
        if (id == null) {
            throw new AuthenticationException();
        }

        var principal = buildPrincipal(user);

        var accessJti = UUID.randomUUID();
        var refreshJti = UUID.randomUUID();
        var access = jwtProvider.generateAccess(accessJti, principal);
        var refresh = jwtProvider.generateRefresh(refreshJti, principal);

        var now = ZonedDateTime.now();
        var expireSeconds = jwtProps.getRefreshTokenExpiredOnSeconds();
        var expireAt = now.plusSeconds(expireSeconds);

        var token = RefreshToken.builder()
            .userId(user.getId())
            .accessJti(accessJti)
            .refreshJti(refreshJti)
            .issuedAt(now)
            .expiredAt(expireAt)
            .build();

        refreshRepository.save(token);

        var dto = new AuthDto(access, refresh);

        return dto;
    }

    @Override
    public AuthDto refresh(TokenRequest request) {
        var tokenValue = request.getToken();
        var decoded = jwtVerifier.verify(tokenValue);

        var jti = UUID.fromString(decoded.getId());
        var old = refreshRepository.findByRefreshJti(jti);
        if (old == null) {
            throw new JwtTokenException();
        }

        var user = usersRepository.findById(old.getUserId())
            .orElseThrow(JwtTokenException::new);

        var principal = buildPrincipal(user);

        var accessJti = UUID.randomUUID();
        var refreshJti = UUID.randomUUID();
        var access = jwtProvider.generateAccess(accessJti, principal);
        var refresh = jwtProvider.generateRefresh(refreshJti, principal);

        var token = RefreshToken.builder()
            .userId(user.getId())
            .accessJti(accessJti)
            .refreshJti(refreshJti)
            .issuedAt(ZonedDateTime.now())
            .expiredAt(ZonedDateTime.now().plusDays(7))
            .build();

        var oldJti = UUID.fromString(decoded.getId());
        refreshRepository.deleteByRefreshJti(oldJti);
        refreshRepository.save(token);

        var dto = new AuthDto(access, refresh);

        return dto;
    }

    @Override
    public void logout(TokenRequest request) {
        var token = request.getToken();
        var decoded = jwtVerifier.verify(token);
        var userId = decoded.getClaim("user_id").asLong();

        refreshRepository.deleteByUserId(userId);
    }

    private Principal buildPrincipal(User user) {
        var roles = user.getRoles().stream()
            .map(Role::getCode)
            .toList();

        var principal = Principal.builder()
            .id(user.getId())
            .login(user.getLogin())
            .roles(roles)
            .build();
        return principal;
    }

}
