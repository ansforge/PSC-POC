package fr.ans.psc.copier.coller.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    public CheckController() {
    }

    @GetMapping(value = "/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("Copier Coller API alive !", HttpStatus.OK);
    }
}
