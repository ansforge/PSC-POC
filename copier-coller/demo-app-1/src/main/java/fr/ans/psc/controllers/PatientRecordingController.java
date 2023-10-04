package fr.ans.psc.controllers;

import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PatientRecordingController {

	@GetMapping("/patient/recorded")
//	public String navigate(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody String jsonSchema) {
	public String navigate(HttpServletRequest request) {
		log.info("PatientRecordingController ..");
		logRequestHeaders(request);
		return "recording-ok";
	}

	public void logRequestHeaders(HttpServletRequest request) {

		Enumeration<String> headersName = request.getHeaderNames();
		Iterator<String> iHeaderNames = headersName.asIterator();
		while (iHeaderNames.hasNext()) {
			String tmp = iHeaderNames.next();
			String value = request.getHeader(tmp);
			log.error("\t" + tmp + ": " + value);
		}
	}
}
