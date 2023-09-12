/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.tools;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.javatuples.Triplet;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ans.psc.client.democlientdam.model.BodyToken;
import fr.ans.psc.client.democlientdam.model.HeaderToken;
import fr.ans.psc.client.democlientdam.model.Token;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {
	// champs de la réponse tokenExchange
	public static final String EX_ACCESS_TOKEN = "access_token";
	public static final String EX_EXPIRES_IN = "expires_in";
	public static final String EX_REFRESH_EXPIRES_IN = "refresh_expires_in";
	public static final String EX_TOKEN_TYPE = "token_type";
	public static final String EX_NOT_BEFORE_POLICY = "not-before-policy";
	public static final String EX_SESSION_STATE = "session_state";
	public static final String EX_SCOPE = "scope";

	private Helper() {
	}

	public static String decodeBase64(String encoded) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(encoded), "UTF-8");
	}

	/*
	 * Décodage et découpage d'un accesToken
	 */
	public static Triplet<String, String, String> splitAndDecodeToken(String token)
			throws JsonMappingException, JsonProcessingException {
		String[] chunks = token.split("\\.");
		String headerToken = null;
		String bodyToken = null;
		String signature = chunks[2];
		try {
			headerToken = decodeBase64(chunks[0]);
			bodyToken = decodeBase64(chunks[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Triplet<String, String, String> splitedDecodedToken = Triplet.with(headerToken, bodyToken, signature);
		return splitedDecodedToken;
	}

	public static Token extractTokenObjectFromDecodedSplitedToken(Triplet<String, String, String> splitedDecodedToken)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		HeaderToken headerToken = mapper.readValue(splitedDecodedToken.getValue0(), HeaderToken.class);
		BodyToken bodyToken = mapper.readValue(splitedDecodedToken.getValue1(), BodyToken.class);
		return new Token(headerToken, bodyToken, splitedDecodedToken.getValue2());
	}

	public static LocalDateTime convertTimeStampToLocalDateTime(String timestampInSecond) {
		Instant instant = Instant.ofEpochSecond(Long.valueOf(timestampInSecond));
		LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
		log.debug("Helper date:" + date);
		return date;
	}

	public static MultiValueMap<String, String> logRequestHeaders(HttpServletRequest request) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		Enumeration<String> headersName = request.getHeaderNames();
		Iterator<String> iHeaderNames = headersName.asIterator();
		while (iHeaderNames.hasNext()) {
			String tmp = iHeaderNames.next();
			String value = request.getHeader(tmp);
			map.add(tmp, value);
			if (!tmp.equalsIgnoreCase("oidc_access_token")) {
				log.info("\t" + tmp + ": " + value);
			}
		}
		return map;
	}

	/*
	 * EXtrait la valeur d'un champ IN d'un json
	 */
	public static String valueOfIntFieldLocalDateTime(String fieldName, String json) {
		JSONObject jsonObj = new JSONObject(json);
		return Integer.toString(jsonObj.getInt(fieldName));
	}

	public static MultiValueMap<String, String> filtredMap(MultiValueMap<String, String> map) {
		MultiValueMap<String, String> filteredMap = new LinkedMultiValueMap<String, String>();

		for (Entry<String, List<String>> data : map.entrySet()) {
			if (data.getKey().startsWith("oidc_claim") || data.getKey().startsWith("x-")
			// || data.getKey().startsWith("oidc_access")
					|| data.getKey().startsWith("authorization")) {
				filteredMap.put(data.getKey(), data.getValue());
			}
		}
		return filteredMap;
	}

	public static String getFullName(MultiValueMap<String, String> map) {
		String firstName = map.getFirst("oidc_claim_given_name");
		firstName = (firstName != null) ? firstName : "-";
		String lastName = map.getFirst("oidc_claim_family_name");
		lastName = (lastName != null) ? lastName : "X";
		return firstName.concat(lastName);
	}
}
