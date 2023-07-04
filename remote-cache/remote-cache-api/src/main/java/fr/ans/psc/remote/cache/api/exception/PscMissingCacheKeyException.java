package fr.ans.psc.remote.cache.api.exception;

import org.springframework.http.HttpStatus;

public class PscMissingCacheKeyException extends PscContextSharingException {
    public PscMissingCacheKeyException() {
        super();
    }

    public PscMissingCacheKeyException(HttpStatus status) {
        super(status);
    }
}
