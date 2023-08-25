package fr.ans.psc.client.democlientdam.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiCallException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ApiCallException(Exception e) {
        super(e);
        StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		log.error("except:" + stacktrace);
    }
	
}
