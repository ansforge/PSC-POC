/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ans.psc.client.democlientdam.calls.ApiCalls;
import fr.ans.psc.client.democlientdam.exception.ApiCallException;
import fr.ans.psc.client.democlientdam.tools.Helper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

@Controller
@Slf4j
public class GetMyDamsController {

	@Autowired
	private ApiCalls api;

	private final static String AUTHORIZATION = "Authorization";
	private final static String OIDC_CLAIM_IDNAT = "oidc_claim_preferred_username";

	@GetMapping("/view")
	public String getMyDam(Model model, HttpServletRequest request)
			throws JsonMappingException, JsonProcessingException, ApiCallException {

		// header: récupération du jeton d'api pour appel API et affichage dans la page
		// page de démonstration
		MultiValueMap<String, String> map = Helper.logRequestHeaders(request);

		String tokenBearer = request.getHeader(AUTHORIZATION);

		String token = tokenBearer.substring("Bearer ".length());
		Triplet<String, String, String> tmp = Helper.splitAndDecodeToken(token);
		model.addAttribute("tokenHeader", tmp.getValue0());
		String bodyToken = tmp.getValue1();
		model.addAttribute("tokenBody", bodyToken);
		String exp = Helper.valueOfIntFieldLocalDateTime("exp", bodyToken);
		String iat = Helper.valueOfIntFieldLocalDateTime("iat", bodyToken);
		model.addAttribute("rawExpDate", exp);
		model.addAttribute("rawIatDate", iat);
		model.addAttribute("expDate", Helper.convertTimeStampToLocalDateTime(exp));
		model.addAttribute("iatDate", Helper.convertTimeStampToLocalDateTime(iat));

		/*
		 * Identité PRosante Connect
		 */
		String fullName = Helper.getFullName(map);
		model.addAttribute("user", fullName);

		// appel à l'API avec le jeton d'API

		log.debug("Appel de l'api ... avec token de body: {} ",
				tokenBearer.substring(tokenBearer.indexOf(".") + 1, tokenBearer.lastIndexOf(".")));
		Pair<HttpStatus, String> damResponse = null;
		try {
			damResponse = api.getMyDams(tokenBearer);

		} catch (IOException | GeneralSecurityException e) {
			return "tech-error";
		}
		log.debug("réponse getMyDams: " + damResponse.getValue0() + " , " + damResponse.getValue1());
		if (damResponse.getValue0() == HttpStatus.OK) {
			model.addAttribute("dams", damResponse.getValue1());
		} else if (damResponse.getValue0() == HttpStatus.GONE) {
			log.debug("réponse getMyDams code 410: pas de données Améli trouvées pour le PS");
			model.addAttribute("status410", HttpStatus.GONE.name());
		} else {
			return "tech-error";
		}

		return "display-dam";
	}

}
