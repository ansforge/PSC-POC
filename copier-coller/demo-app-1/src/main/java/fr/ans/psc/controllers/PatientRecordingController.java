/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PatientRecordingController {

	@GetMapping("/patient/recorded")
	public String navigate() {
		log.debug("PatientRecordingController ..");
		return "recording-ok";
	}
}
