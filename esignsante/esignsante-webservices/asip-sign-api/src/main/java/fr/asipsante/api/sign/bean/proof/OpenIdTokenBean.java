/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.bean.proof;

/**
 * Classe permettant de stocker des paramètres d'un OpenId token.
 */
public class OpenIdTokenBean {

    /** la réponse du endpoint de vérification du jeton */
    private String introspectionResponse;
    
    /** le détail des informations utilisateur */
    private String userInfo;
    
    /** Valeur du token ProSanteConnect */
    private String accessToken;
    
    /** Valeur du token d'API */
    private String apiToken;

	public String getIntrospectionResponse() {
		return introspectionResponse;
	}

	public void setIntrospectionResponse(String introspectionResponse) {
		this.introspectionResponse = introspectionResponse;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	
}
