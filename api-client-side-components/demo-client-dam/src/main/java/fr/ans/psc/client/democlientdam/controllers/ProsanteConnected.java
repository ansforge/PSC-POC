/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.controllers;

import javax.servlet.http.HttpServletRequest;

import org.javatuples.Triplet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ans.psc.client.democlientdam.tools.Helper;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class ProsanteConnected {
	
	private final static String OIDC_ACCESS_TOKEN =  "oidc_access_token";

	@GetMapping("/psc")
	public String getApiToken(Model model, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		log.debug("Demande de page d'accueil d'un utilisateur prosanté connecté");
		
		/*
		 * Lecture des headers (données renvoyées par le serveur uniquement dans le cadre d'une démontsration). 
		 */
		MultiValueMap<String, String> map = Helper.logRequestHeaders(request);
//		MultiValueMap<String, String> filetredMap = Helper.filtredMap(map);
//		model.addAttribute("mapHeaders",filetredMap);

		/*
		 * Identité PRosante Connect
		 */
	    String fullName = Helper.getFullName (map);
		model.addAttribute("user", fullName);
		
		/*
		 * Récupération du token et de ses caractéristiques ...
		 */

		Boolean bExisteToken = map.containsKey(OIDC_ACCESS_TOKEN);
		if (bExisteToken) {
		String token = request.getHeader(OIDC_ACCESS_TOKEN);
		
		model.addAttribute("expDate", Helper.convertTimeStampToLocalDateTime(map.getFirst("oidc_claim_exp")));
		model.addAttribute("iatDate", Helper.convertTimeStampToLocalDateTime(map.getFirst("oidc_claim_iat")));
		model.addAttribute("rawExpDate", map.getFirst("oidc_claim_exp"));
		model.addAttribute("rawIatDate", map.getFirst("oidc_claim_iat"));
		
		Triplet<String, String, String> tmp = Helper.splitAndDecodeToken(token);	
		//log.debug("token psc reçu: {} ", token);
		model.addAttribute("tokenHeader", tmp.getValue0());
		model.addAttribute("tokenBody",tmp.getValue1());
		}
		return "psc-connected";
	}
}
