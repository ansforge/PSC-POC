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


//	@Autowired
//	private ApiCalls api;

	private final static String AUTHORIZATION = "Authorization";
	private final static String OIDC_CLAIM_IDNAT = "oidc_claim_preferred_username";

	@GetMapping("/view")
	public String getMyDam(Model model, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, ApiCallException {

		
		
		//header: récupération du jeton d'api pour appel API et affichage dans la page page de démonstration
		MultiValueMap<String,String> map = Helper.logRequestHeaders(request);
		MultiValueMap<String, String> filetredMap = Helper.filtredMap(map);
		model.addAttribute("mapHeaders",filetredMap);
		String tokenBearer = request.getHeader(AUTHORIZATION);		
		String token = tokenBearer.substring("Bearer ".length());
//		model.addAttribute("token",token);
		Triplet<String, String, String> tmp = Helper.splitAndDecodeToken(token);	
		model.addAttribute("tokenHeader", tmp.getValue0());
		String bodyToken = tmp.getValue1();
		model.addAttribute("tokenBody",bodyToken);
		String exp = Helper.valueOfIntFieldLocalDateTime("exp",bodyToken);
		String iat = Helper.valueOfIntFieldLocalDateTime("iat",bodyToken);
		model.addAttribute("rawExpDate",exp);
		model.addAttribute("rawIatDate", iat);		
		model.addAttribute("expDate",Helper.convertTimeStampToLocalDateTime(exp));
		model.addAttribute("iatDate",Helper.convertTimeStampToLocalDateTime(iat));	
		
		//header: récupération de l' idNat pour appel API 
	//	String idNat = request.getHeader(OIDC_CLAIM_IDNAT);		
	//	log.debug("Reception d'une demande de DAM (getMyDAMs pour l'IdNat: {} et avec le token d'API {}", idNat, token);
		

		// appel à l'API avec le jeton d'API 
/*
		log.debug("Appel de l'api ... avec token Bearer: {} ", tokenBearer);
		Pair<HttpStatus,String> damResponse = null;
		try {		
			damResponse = api.getMyDams(tokenBearer);
			
		} catch (IOException | GeneralSecurityException e) {
			return "tech-error";
		}
		log.debug("réponse getMyDams: " + damResponse.getValue0() + " , " + damResponse.getValue1());
		if (damResponse.getValue0()==HttpStatus.OK) {
			model.addAttribute("dams",damResponse.getValue1());		
		}
		else if (damResponse.getValue0()==HttpStatus.GONE) {
			log.debug("réponse getMyDams code 410: pas de données Améli trouvées pour le PS" );
			model.addAttribute("status410", HttpStatus.GONE.name());
		}
		else {
			return "tech-error";
		}
*/
		return "display-dam";
	}

/*	
	@GetMapping("/view")
	public String getMyDamDev(Model model, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, ApiCallException {

		String dam = "{\"nationalId\":\"30B0184230/CPET00002\",\"dams\":[{\"identifiantLieuDeTravail\":\"0B0184230\",\"typeIdentifiant\":\"FINESS / N° de registre\",\"codeTypeIdentifiant\":\"3\",\"raisonSociale\":\"CENTRE DE SANTE RPPS18423\",\"modeExercice\":\"Salarié\",\"codeModeExercice\":\"1\",\"numActivite\":\"2103523968\",\"numAssuranceMaladie\":\"000184234\",\"dateDebutValidite\":\"22-03-2022\",\"dateFinValidite\":\"22-03-2025\",\"specialite\":\"Non définie\",\"codeSpecialite\":\"99\",\"conventionnement\":\"Etablissement ou Centre de Santé\",\"codeConventionnel\":\"9\",\"indicateurFacturation\":\"Libellé indicateur facturation 2\",\"codeIndicateurFacturation\":\"2\",\"zoneIK\":\"code non trouvé dans la nomenclature\",\"codeZoneIK\":\"9\",\"zoneTarifaire\":\"Non définie\",\"codeZoneTarifaire\":\"99\",\"agrement1\":\"Non défini\",\"codeAgrement1\":\"99\",\"agrement2\":\"Non défini\",\"codeAgrement2\":\"99\",\"agrement3\":\"Non défini\",\"codeAgrement3\":\"99\",\"habilitationFse\":\"000\",\"habilitationLot\":\"001\"}]}";
		
		
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIeWs5eXpfejYzQTRGb19TUTFIdkMwS0VMT2s3TXUtTVRvWTlQYzNUZ0tzIn0.eyJleHAiOjE2OTQxODA5OTEsImlhdCI6MTY5NDE2NjU5MSwianRpIjoiZjA5OGYxNjQtN2U0ZS00ODgxLWEyZTYtNjkyNWFjNzQ0N2U3IiwiaXNzIjoiaHR0cHM6Ly9hdXRoLnNlcnZlci5wc2MucG9jcy5lc2FudGUuZ291di5mci9yZWFsbXMvcG9jIiwiYXVkIjoiZGFtIiwic3ViIjoiYjQ2YzhkNTQtMTBmYi00ODFlLWE5NmItZDllNmY4ZmFiNWM4IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHJveHktcG9jIiwic2Vzc2lvbl9zdGF0ZSI6IjE1ZjYxMjU0LTY4ZmUtNDcxNy1hMzY2LThmMWRkOGE1YzRlYSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJzY29wZSI6IiIsInNpZCI6IjE1ZjYxMjU0LTY4ZmUtNDcxNy1hMzY2LThmMWRkOGE1YzRlYSIsIlN1YmplY3ROYW1lSUQiOiJhbnMyMDIyMDcyMDE1MjgxMyIsImNuZiI6eyJ4NXQjUzI1NiI6Ik9UWmlNMlU0TlRka1lUTm1OVGhoTnpsbU4yWXdPREZpWW1JM1ptUTBNV0ZtWVdRNFptSXhPR1kwWVdKbFpqRTFZemRpTURjelpESmpNamhrTnpaaFlnPT0ifX0.X5iVJrmzdh8XI7IUZPgj9WxObWiR5cfxX5rm0ibQNyA0H6n8SuEakagDnscQE9y5vbbq8c1nRPfffzZ8iYI3VqwaCva_RWtSxsxlMw_xij5kn7n9hzUsUVY0bvnhKEZ7Tb6oVGHJ9bcrTm3bbCfs4FJPGMTmwf3ZiJM9fMbLKMaIHDNmI40RqVkBLeNtghr1URYGZ2W-Qr3cbik2YKLJ7DkZDcRt_vEYpQXZxx0vQ41PdssFyI3yD9XX5cp3A4HP_-Ck6bHp2-hqNjUit-whbh-4H_0NmUxBdZj7hZgYTniI7puY_5PU4TCM7CM9S-NGrYQ6LlfBizDnw7D-exfmnQ";
		model.addAttribute("token",token);
		Triplet<String, String, String> tmp = Helper.splitAndDecodeToken(token);	
		model.addAttribute("tokenHeader", tmp.getValue0());
		String bodyToken = tmp.getValue1();
		model.addAttribute("tokenBody",bodyToken);
		String exp = Helper.valueOfIntFieldLocalDateTime("exp",bodyToken);
		String iat = Helper.valueOfIntFieldLocalDateTime("iat",bodyToken);
		model.addAttribute("rawExpDate",exp);
		model.addAttribute("rawIatDate", iat);		
		model.addAttribute("expDate",Helper.convertTimeStampToLocalDateTime(exp));
		model.addAttribute("iatDate",Helper.convertTimeStampToLocalDateTime(iat));	
		
	

		// appel à l'API avec le jeton d'API 
		
			model.addAttribute("dams",dam);		

		return "display-dam";
	}
	*/
	
}
