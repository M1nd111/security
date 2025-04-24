package plugin.acc.auth;

import lombok.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final JwtAuthFilter jwtFilter;

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain filterChain(
        HttpSecurity http,
        AllowedUrls allowedUrls
    ) throws Exception {
        return http.cors(customizer -> {})
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(c -> c
                .authenticationEntryPoint(jwtAuthEntryPoint))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(c -> c
                .requestMatchers(allowedUrls.getAllowedUrls()).permitAll()
                .anyRequest().authenticated())
            .sessionManagement(c -> c
                .sessionCreationPolicy(STATELESS))
            .build();
    }

}
