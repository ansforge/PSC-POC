package fr.ans.psc.copier.coller.api.exception;

import org.springframework.http.HttpStatus;

public class PscSchemaException extends PscContextSharingException {
    public PscSchemaException() {
    	// ne pas renvoyer une erreur 500 pour une non conformité au schéma
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public PscSchemaException(HttpStatus status) {
    	// ne pas renvoyer une erreur 500 pour une non conformité au schéma
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
