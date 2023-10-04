package fr.ans.psc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SimpleFormController {

	@GetMapping("/consultation/first")
	public String getFirstSimpleFormPage(HttpServletRequest request, HttpServletResponse response) {
		log.debug("SimpleFormController ../consultation/first");
		javax.servlet.http.Cookie co = WebUtils.getCookie(request, "sts_token");
		if (co == null) {
			log.error("token not found in request");
		}
		response.addCookie(co);
		return "form-page";
	}

	@GetMapping("/consultation/form")
	public String getSimpleFormPage(HttpServletRequest request) {
		log.debug("getSimpleFormPage ../consultation/form");
		return "form-page";
	}
}
