package plugin.acc.auth.dto;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;

import static lombok.AccessLevel.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Schema(description = "Запрос аутентификации")
public class LoginRequest {

    @Schema(description = "Логин пользователя")
    private String login;

    @Schema(description = "Пароль пользователя")
    private String password;

}
