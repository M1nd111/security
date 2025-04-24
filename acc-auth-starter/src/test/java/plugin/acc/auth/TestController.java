package plugin.acc.auth;

import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/open")
    @PreAuthorize("permitAll()")
    public void getOpen() {

    }

    @GetMapping("/secure")
    public void getSecure() {

    }

}
