package fr.ans.psc.dam.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.ans.psc.dam.model.Activity;
import fr.ans.psc.dam.model.UsefulUserInfo;
import fr.ans.psc.dam.model.UserActivities;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {

	// champs du UserInfo
	public static final String GIVEN_NAME = "given_name";
	public static final String PREFERRED_USERNAME = "preferred_username";
	public static final String SUBJECT_ORGANIZATION = "SubjectOrganization";
	public static final String FAMILY_NAME = "family_name";
	public static final String IDNAT = "SubjectNameID";
	public static final String SUBJECT_REF_PRO = "SubjectRefPro";
	public static final String EXERCICES = "exercices";
	public static final String CODE_PROFESSION = "codeProfession";
	public static final String NOM_D_EXERCICE = "nomDexercice";
	public static final String ACTIVITES = "activities";
	public static final String ID_TECH_STRUCTURE = "identifiantTechniqueDeLaStructure";
	public static final String MODE_EXERCICE = "codeModeExercice";
	public static final String RAISON_SOCIALE_SITE = "raisonSocialeSite";

	private Helper() {
	}

	public static String encodeBase64(String stringToEncode) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(stringToEncode.getBytes("UTF-8"));
	}

	public static String decodeBase64toString(String stringToDecode)
			throws UnsupportedEncodingException, IllegalArgumentException {
		byte[] decodedBytes = Base64.getDecoder().decode(stringToDecode);
		return new String(decodedBytes, "UTF-8");
	}

	public static String safeUrlDecodeBase64toString(String stringToDecode)
			throws UnsupportedEncodingException, IllegalArgumentException {
		byte[] decodedBytes = Base64.getUrlDecoder().decode(stringToDecode);
		return new String(decodedBytes, "UTF-8");
	}

//	public static byte[] decodeBase64toByteArray(String stringToDecode) throws UnsupportedEncodingException, IllegalArgumentException {
//		return Base64.getDecoder().decode(stringToDecode);
//	}

	public static UsefulUserInfo jsonStringToUserInfo(String sUserInfo)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		return new ObjectMapper().readValue(sUserInfo, UsefulUserInfo.class);
	}

	public static UserActivities getUserActivities(String jsonUserInfo)
			throws JsonMappingException, JsonProcessingException {
		log.debug("Parsing du userinfo:  {}", jsonUserInfo);
		List<Activity> activities = new ArrayList<Activity>();
		JsonNode nodeExercices;
		JsonNode nodeExerciceX;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		String idNational = null;
		rootNode = mapper.readTree(jsonUserInfo);
		idNational = rootNode.get(IDNAT).textValue();		
		log.debug(".. idnat ({}) trouvé : {}", IDNAT.toString(), idNational);

		if (rootNode.get(SUBJECT_REF_PRO).has(EXERCICES)) {
			nodeExercices = rootNode.get(SUBJECT_REF_PRO).get(EXERCICES);

			for (int exer = 0; exer < nodeExercices.size(); exer++) {
				nodeExerciceX = nodeExercices.get(exer);
				log.debug(".. recherche activité pour  l'exercice d'indice: {}", exer);
				activities = getActivities(nodeExerciceX, activities);
			}
		}
		UserActivities userActivities = new UserActivities(idNational, activities);
		return userActivities;
	}

	public static List<Activity> getActivities(JsonNode nodeExerciceX,List<Activity> activities ) {
		JsonNode nodeAct;
			if (nodeExerciceX.has(ACTIVITES)) {
				int nbActivites = nodeExerciceX.get(ACTIVITES).size();
				log.debug(".. nombre d'activités trouvées: {}", nbActivites);
				for (int act = 0; act < nbActivites; act++) {
					nodeAct = nodeExerciceX.get(ACTIVITES).get(act);
					Activity activity = new Activity(nodeAct.get(ID_TECH_STRUCTURE).textValue(),
							nodeAct.get(MODE_EXERCICE).textValue(), nodeExerciceX.get(CODE_PROFESSION).textValue(),
							nodeExerciceX.get(NOM_D_EXERCICE).textValue(), nodeAct.get(RAISON_SOCIALE_SITE).textValue());
					activities.add(activity);
					log.debug(".. identifiant technique de la structure: {}, raison sociale: {}, mode d'exercice: {}",
							activity.getIdentifiantTechniqueDeLaStructure(), activity.getRaisonSocialeSite(), activity.getCodeModeExercice());
				}
		}
		return activities;
	}
}
