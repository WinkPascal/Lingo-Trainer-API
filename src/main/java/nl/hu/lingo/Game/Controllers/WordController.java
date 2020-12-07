package nl.hu.lingo.Game.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordController {
@GetMapping("/")
    public String getHelloWorld(){
        return "hello world";
    }
}