/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ConsultationRecordingController {

	@GetMapping("/consultation/recorded")
	public String navigate() {
		log.debug("ConsultationRecordingController");
		return "recording-ok";
	}
}
