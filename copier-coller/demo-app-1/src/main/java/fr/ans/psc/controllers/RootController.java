package fr.ans.psc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RootController {

	@GetMapping("/")
	public String navigate() {
		log.debug("RootController ..");
		return "index";
	}
}
