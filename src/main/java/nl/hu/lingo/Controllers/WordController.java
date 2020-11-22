package nl.hu.lingo.Controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordController {
@GetMapping("/hello")
    public String getHelloWorld(){
        return "hello world";
    }
}