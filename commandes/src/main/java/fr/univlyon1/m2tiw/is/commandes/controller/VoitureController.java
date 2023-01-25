package fr.univlyon1.m2tiw.is.commandes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class VoitureController {
    @GetMapping()
    public ResponseEntity<String> test() {
        return new ResponseEntity<String>("hello", HttpStatus.OK);
    }
}
