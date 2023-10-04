package fr.ans.psc.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LogoutController {

	@GetMapping("/consultation/logout")
	public String navigate(HttpServletRequest request, HttpServletResponse response) {
		log.debug("LogoutController");
		javax.servlet.http.Cookie co = new Cookie("sts_token", null);
		co.setMaxAge(0);
		response.addCookie(co);
		javax.servlet.http.Cookie psc = new Cookie("mod_auth_openidc_session", null);
		psc.setMaxAge(0);
		response.addCookie(psc);
		return "bye";
	}
}
