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

@Controller
public class ProsanteConnected {
	
	private final static String OIDC_ACCESS_TOKEN =  "oidc_access_token";

	@GetMapping("/psc")
	public String getApiToken(Model model, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		System.out.println("Demande de page d'accueil d'un utilisateur prosanté connecté");
		
		/*
		 * Lecture des headers ...
		 */
		MultiValueMap<String, String> map = Helper.logRequestHeaders(request);
		MultiValueMap<String, String> filetredMap = Helper.filtredMap(map);
		model.addAttribute("mapHeaders",filetredMap);

		/*
		 * REcup du token et de ses caratcéristiques ...
		 */

		Boolean bExisteToken = map.containsKey(OIDC_ACCESS_TOKEN);
		if (bExisteToken) {
		//String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJKRlRBM1llVVdQbERCTEJfeU5qWUs0bWZJcTdhYXBBS21ieVdyczRPZ0RnIn0.eyJleHAiOjE2OTA0Njk4NDgsImlhdCI6MTY5MDQ2OTcyOCwiYXV0aF90aW1lIjoxNjkwNDY5NzI4LCJqdGkiOiI2YTMyYTgxZC02MGM2LTQ3ODctYjgyOS00NzEyOGUxNjU4NjUiLCJpc3MiOiJodHRwczovL2F1dGguYmFzLnBzYy5lc2FudGUuZ291di5mci9hdXRoL3JlYWxtcy9lc2FudGUtd2FsbGV0Iiwic3ViIjoiZjo1NTBkYzFjOC1kOTdiLTRiMWUtYWM4Yy04ZWI0NDcxY2Y5ZGQ6QU5TMjAyMjA3MjAxNTI4MTMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhbnMtcG9jLWVzaWduc2FudGUtYmFzIiwibm9uY2UiOiItVzRvWV9qRXdzSVRKQldBRHVJWTBjSHRYakpmaTdVdXp1b09GV2RHR0xjIiwic2Vzc2lvbl9zdGF0ZSI6IjY1Y2ZmZmY1LWY1YmQtNDcxNC04NWU3LTdmNjU4Y2VlNTdlZSIsImFjciI6ImVpZGFzMSIsInNjb3BlIjoib3BlbmlkIHNjb3BlX2FsbCBpZGVudGl0eSIsInNpZCI6IjY1Y2ZmZmY1LWY1YmQtNDcxNC04NWU3LTdmNjU4Y2VlNTdlZSIsImF1dGhNb2RlIjoiTU9CSUxFIiwib3RoZXJJZHMiOlt7ImlkZW50aWZpYW50IjoiQU5TMjAyMjA3MjAxNTI4MTMiLCJvcmlnaW5lIjoiRURJVCIsInF1YWxpdGUiOjF9XSwiU3ViamVjdE5hbWVJRCI6IkFOUzIwMjIwNzIwMTUyODEzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiQU5TMjAyMjA3MjAxNTI4MTMifQ.flK_AXHUdDp805EiyfBWENjLLHbgopqKaHUk5KQo3b8fpZ3oraB8OHe97cz585V0U3Tec3-zIvjc84P_rGrWdo-CFWAZ3eWyHzRrUwoakNs-KrIyVzAVaqQbyArVHyKm3NJMCeQ58g0AtLQpOB6HhUlbiQwzRgj9Rur50fZyhdund0Pjvz40BcGVgaAwKzgwZGsSoFX6BMTiy0WV1bfgMDJaB8C1X4Pa8fwIpKLUTWIg9E8Iqvwy5e4TqB8BwG2NNqWbwdFE92q16kKv04qZV5IkKuOiyZ37i7-12LwDcgw6Qj4szfAZ3sK-yk6iGWljI28Tfwf4sOWi8DCh8XPQTw";
		String token = request.getHeader(OIDC_ACCESS_TOKEN);
		model.addAttribute("token", token);
		
		model.addAttribute("expDate", Helper.convertTimeStampToLocalDateTime(map.getFirst("oidc_claim_exp")));
		model.addAttribute("iatDate", Helper.convertTimeStampToLocalDateTime(map.getFirst("oidc_claim_iat")));
		model.addAttribute("rawExpDate", map.getFirst("oidc_claim_exp"));
		model.addAttribute("rawIatDate", map.getFirst("oidc_claim_iat"));
		
		Triplet<String, String, String> tmp = Helper.splitAndDecodeToken(token);	
		model.addAttribute("tokenHeader", tmp.getValue0());
		model.addAttribute("tokenBody",tmp.getValue1());
		}
		return "psc-connected";
	}
}
