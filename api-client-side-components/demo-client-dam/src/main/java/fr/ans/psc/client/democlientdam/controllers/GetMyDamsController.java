package fr.ans.psc.client.democlientdam.controllers;

import javax.servlet.http.HttpServletRequest;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ans.psc.client.democlientdam.calls.ApiCalls;
import fr.ans.psc.client.democlientdam.tools.Helper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

@Controller
@Slf4j
public class GetMyDamsController {
	
	 @Autowired
    private ApiCalls api;

	 private final static String AUTHORIZATION =  "authorization";
	 
	@GetMapping("/view")
	public String getMyDam(Model model, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {

		
		
		//header
		MultiValueMap<String,String> map = Helper.logRequestHeaders(request);
		MultiValueMap<String, String> filetredMap = Helper.filtredMap(map);
		model.addAttribute("mapHeaders",filetredMap);
		Boolean bExisteToken = map.containsKey(AUTHORIZATION);
		
		if (bExisteToken) {
		String token = request.getHeader(AUTHORIZATION);
		token = token.substring("Bearer ".length());
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
		}		

		// appel à l'API avec le jeton d'API 
		log.info("Appel de l'api ...");
		String damResponse = api.getMyDams();
	//	String damResponse ="{\"nationalId\":\"899700245667\",\"dams\":[{\"identifiantLieuDeTravail\":\"99700245667008\",\"typeIdentifiant\":\"Id Cabinet RPPS / N° de registre\",\"codeTypeIdentifiant\":\"6\",\"raisonSociale\":\"CABINET M DOC0024566\",\"modeExercice\":\"Libéral\",\"codeModeExercice\":\"0\",\"numActivite\":\"2102887019\",\"numAssuranceMaladie\":\"001055664\",\"dateDebutValidite\":\"26-06-2020\",\"dateFinValidite\":\"26-06-2023\",\"specialite\":\"Médecine générale\",\"codeSpecialite\":\"01\",\"conventionnement\":\"Conventionné\",\"codeConventionnel\":\"1\",\"indicateurFacturation\":\"Libellé indicateur facturation 2\",\"codeIndicateurFacturation\":\"2\",\"zoneIK\":\"Libellé Code Indemnités kilométriques 1\",\"codeZoneIK\":\"1\",\"zoneTarifaire\":\"Zone B\",\"codeZoneTarifaire\":\"24\",\"agrement1\":\"code non trouvé dans la nomenclature\",\"codeAgrement1\":\"00\",\"agrement2\":\"code non trouvé dans la nomenclature\",\"codeAgrement2\":\"00\",\"agrement3\":\"code non trouvé dans la nomenclature\",\"codeAgrement3\":\"00\",\"habilitationFse\":\"001\",\"habilitationLot\":\"001\"}]}";
		log.info("réponse getMyDams: " + damResponse);
		model.addAttribute("dams",damResponse);
		return "display-dam";
	}
}
