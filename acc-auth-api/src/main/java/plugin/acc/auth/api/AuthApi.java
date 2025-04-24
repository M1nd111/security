package plugin.acc.auth.api;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.web.bind.annotation.*;

import plugin.acc.auth.dto.*;

@RequestMapping("api/auth")
@Tag(name = "Авторизация")
public interface AuthApi {

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя")
    AuthDto login(LoginRequest request);

    @PostMapping("/refresh")
    @Operation(summary = "Обновление пары JWT токенов")
    AuthDto refresh(TokenRequest request);

    @PostMapping("/logout")
    @Operation(summary = "Удаление активных сессий")
    void logout(TokenRequest request);

}
