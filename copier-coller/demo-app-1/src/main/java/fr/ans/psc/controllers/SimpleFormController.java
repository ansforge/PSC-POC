package fr.ans.psc.controllers;

import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SimpleFormController {

	@GetMapping("/patient/first")
	public String getFirstFormPage(HttpServletRequest request, HttpServletResponse response) {
		log.info("SimpleFormController ../patient/first");
		logRequestHeaders(request);
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		log.info("lecture de tous les cookies..");
		for (javax.servlet.http.Cookie cookie : cookies) {
			log.info(" name {} : value {} : isHttpOnly {}", cookie.getName(), cookie.getValue(), cookie.isHttpOnly());
		}
		log.info("fin de lecture des cookies..");
		javax.servlet.http.Cookie co = WebUtils.getCookie(request, "sts_token");
		String re = ((co == null) ? "not found" : co.getValue());
		log.info("sts_token: {}", re);
		response.addCookie(co);
		return "form-page";
	}

	@GetMapping("/patient/form")
	public String getSimpleFormPage(HttpServletRequest request /* , HttpServletResponse response */) {
		log.info("SimpleFormController ../patient/form");
		logRequestHeaders(request);
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		log.info("lecture de tous les cookies..");
		for (javax.servlet.http.Cookie cookie : cookies) {
			log.info(" name {} : value {} : isHttpOnly {}", cookie.getName(), cookie.getValue(), cookie.isHttpOnly());
		}
		log.info("fin de lecture des cookies..");
		javax.servlet.http.Cookie co = WebUtils.getCookie(request, "sts_token");
		String re = ((co == null) ? "not found" : co.getValue());
		log.info("sts_token: {}", re);
		// response.addCookie(co);
		return "form-page";
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
