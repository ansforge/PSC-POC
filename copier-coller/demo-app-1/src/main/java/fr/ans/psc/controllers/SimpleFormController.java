package fr.ans.psc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SimpleFormController {

	@GetMapping("/patient/form")
	public String getSimpleFormPage(HttpServletRequest request) {
		log.info("SimpleFormController ..");
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		log.info("lecture de tous les cookies..");
		for (javax.servlet.http.Cookie cookie : cookies) {
			log.info(" name {} : value {}", cookie.getName(), cookie.getValue());	
		}
		log.info("fin de lecture des cookies..");
		javax.servlet.http.Cookie co = WebUtils.getCookie(request, "sts_token");
		String re = ((co == null) ? "not found": co.getValue());
		log.info("sts_token: {}", re);		
		return "form-page";
	}
}

