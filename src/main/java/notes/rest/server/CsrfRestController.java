package notes.rest.server;

import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = {"http://localhost:8081/", "http://localhost:8082/"})
public class CsrfRestController {

    @GetMapping("/api/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

}
