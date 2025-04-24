package plugin.acc.auth;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@Configuration
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public AllowedUrls allowedUrls() {
        return () -> new String[]{"/open"};
    }

}
