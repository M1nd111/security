package plugin.acc.auth.config;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.*;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

//@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private String[] allowedUrls = new String[]{
        "/swagger-ui/**",
        "/v3/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(customizer -> {})
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(c -> c
                .requestMatchers(allowedUrls).permitAll()
                .anyRequest().authenticated())
            .sessionManagement(c -> c
                .sessionCreationPolicy(STATELESS))
            .build();
    }

}
