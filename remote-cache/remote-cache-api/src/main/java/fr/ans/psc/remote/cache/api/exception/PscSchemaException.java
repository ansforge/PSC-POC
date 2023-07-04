package fr.ans.psc.remote.cache.api.exception;

import org.springframework.http.HttpStatus;

public class PscSchemaException extends PscContextSharingException {
    public PscSchemaException() {
        super();
    }

    public PscSchemaException(HttpStatus status) {
        super(status);
    }
}
