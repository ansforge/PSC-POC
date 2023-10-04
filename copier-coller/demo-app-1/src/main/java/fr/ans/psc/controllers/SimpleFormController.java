package fr.ans.psc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SimpleFormController {

	@GetMapping("/patient/first")
	public String getFirstFormPage(HttpServletRequest request, HttpServletResponse response) {
		log.debug("SimpleFormController ../patient/first");
		javax.servlet.http.Cookie co = WebUtils.getCookie(request, "sts_token");
		if (co == null) {
			log.error("token not found in request");
		}
		response.addCookie(co);
		return "form-page";
	}

	@GetMapping("/patient/form")
	public String getSimpleFormPage(HttpServletRequest request) {
		log.debug("SimpleFormController ../patient/form");
		return "form-page";
	}
}
