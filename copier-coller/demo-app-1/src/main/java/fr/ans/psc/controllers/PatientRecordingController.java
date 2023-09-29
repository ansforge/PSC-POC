package fr.ans.psc.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PatientRecordingController {

	@GetMapping("/patient/recorded")
//	public String navigate(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody String jsonSchema) {
	public String navigate() {
		log.debug("PatientRecordingController ..");
//		log.debug("token: {}", token);
//		log.debug("body: {}", jsonSchema);
		return "recording-ok";
	}
}
