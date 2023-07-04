package fr.ans.psc.dam.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;

import fr.ans.psc.dam.api.exception.ThrowDamException;
import fr.ans.psc.dam.model.PsDAMs;
import fr.ans.psc.dam.util.Helper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DamsApiImpl implements DamsApiDelegate {

	public static final String HEADER_NAME_USERINFO = "X-BASE64-USERINFO";
	public static final String HEADER_NAME_IDNAT = "X-IDNAT";

	@Autowired
	ApiExecutor exec;

	@Override
	public ResponseEntity<PsDAMs> userdams(String idNational, Boolean dontFermes, String idTechniqueStructure,
			String modeExercice) {
		log.debug("APPEL userDAMs ");
		log.debug("    pour idNational: {}, dontFermes: {}, idTechniqueStructure: {}, modeExercice: {}", idNational,
				dontFermes, idTechniqueStructure, modeExercice);
		idNational = idNational.replace("%2F", "/");
		PsDAMs psDAMs = exec.getDAMs(idNational, dontFermes, idTechniqueStructure, modeExercice);
		return new ResponseEntity<PsDAMs>(psDAMs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<PsDAMs> mydams() {
		log.debug("APPEL mydams");
		String idNat = extractIdNationalFromHeaders();
		PsDAMs psDAMs = exec.getDAMs(idNat, false, null, null);
		return new ResponseEntity<PsDAMs>(psDAMs, HttpStatus.OK);
	}

	public HttpServletRequest getHttpRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attrs.getRequest();
	}

	public String extractIdNationalFromHeaders() {
		List<String> tmp = new ArrayList<String>();
		Enumeration<String> enumIDNAT = getHttpRequest().getHeaders(HEADER_NAME_IDNAT);
		int count = 0;
		String idNat = "";
		while (enumIDNAT.hasMoreElements()) {
			idNat = enumIDNAT.nextElement();
			log.debug("idNAt extrait des headers!: {} ", idNat);
			count++;
		}

		if (count == 0) {
			ThrowDamException.throwExceptionRequestError(
					"Header " + HEADER_NAME_IDNAT + " non trouvé dans les headers de la requête ",
					HttpStatus.BAD_REQUEST);
		}

		if (count > 1) {
			ThrowDamException.throwExceptionRequestError(
					"Plusieurs Headers " + HEADER_NAME_IDNAT + " trouvés dans la requête", HttpStatus.BAD_REQUEST);
		}

		return idNat;
	}
	
	
	public String extractUserInfoFromHeaders() {
		List<String> tmp = new ArrayList<String>();
		Enumeration<String> enumJsonUserinfo = getHttpRequest().getHeaders(HEADER_NAME_USERINFO);
		int count = 0;
		String jsonUserInfo = "";
		while (enumJsonUserinfo.hasMoreElements()) {
			jsonUserInfo = enumJsonUserinfo.nextElement();
			log.debug("userinfo base64: {} ", enumJsonUserinfo);
			count++;
		}

		if ((enumJsonUserinfo == null) || (count == 0)) {
			ThrowDamException.throwExceptionRequestError(
					"Header " + HEADER_NAME_USERINFO + " non trouvé dans les headers de la requête ",
					HttpStatus.BAD_REQUEST);
		}

		if (count > 1) {
			ThrowDamException.throwExceptionRequestError(
					"Plusieurs Headers " + HEADER_NAME_USERINFO + " trouvés dans la requête", HttpStatus.BAD_REQUEST);
		}

		log.debug("userinfo base64: {} ", jsonUserInfo);
		try {
			jsonUserInfo = Helper.decodeBase64toString(jsonUserInfo);
		} catch (UnsupportedEncodingException | IllegalArgumentException e) {
			ThrowDamException.throwExceptionRequestError("Erreur sur le décodage du UserInfo fourni",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.debug("userinfo  ", jsonUserInfo);
		return jsonUserInfo;
	}

}
