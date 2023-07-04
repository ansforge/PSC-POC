package fr.ans.psc.copier.coller.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ans.psc.copier.coller.api.exception.PscSchemaException;
import fr.ans.psc.copier.coller.api.service.DiscoveryService;

@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    @Autowired
    private DiscoveryService discovery;
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<String> getAllRegisteredSchemas() {
        try {
            List<String> schemas = discovery.getAllSchemas();
            return new ResponseEntity<>(schemas.toString(), HttpStatus.OK);
        } catch (PscSchemaException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{schemaId}", produces = {"application/json"})
    public ResponseEntity<String> getSchemaById(@PathVariable String schemaId) {
        try {
            String schema = discovery.getPscJsonContextSchema(schemaId);
            return new ResponseEntity<>(schema, HttpStatus.OK);
        } catch (PscSchemaException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createNewSchema(@RequestBody String schema) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping
    public ResponseEntity<String> updateSchema(@RequestBody String schema) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
