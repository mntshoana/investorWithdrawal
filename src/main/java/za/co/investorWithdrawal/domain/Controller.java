package za.co.investorWithdrawal.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
    @GetMapping(value = "api/v1/helloWorld",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkCachedUser() {
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }
}
