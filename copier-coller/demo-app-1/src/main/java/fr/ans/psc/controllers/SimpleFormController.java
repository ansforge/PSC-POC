package fr.ans.psc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SimpleFormController {

	@GetMapping("/patient/form")
	public String getSimpleFormPage(HttpServletRequest request) {
		log.info("SimpleFormController ..");
		javax.servlet.http.Cookie co = WebUtils.getCookie(request, "sts-token");
		String re = ((co == null) ? "not found": co.getValue());
		log.info("sts_token: {}", re);		
		return "form-page";
	}
}
