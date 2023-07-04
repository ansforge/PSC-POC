package fr.ans.psc.remote.cache.api.controller;

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
        return new ResponseEntity<>("Remote Cache API alive !", HttpStatus.OK);
    }
}
