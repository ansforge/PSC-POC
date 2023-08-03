package fr.ans.psc.client.democlientdam.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyToken {

	//TOKEN PROPERTIES
	@JsonProperty("exp")
//	@NotNull(message = "expiration date should not be null")
	private String expirationDate;
	
	@JsonProperty("iat")
	private String issuedAt;
	
	@JsonProperty("jti")
	private String jwtId;
	
	@JsonProperty("iss")
	private String issuer;
	
	@JsonProperty("aud")
	private String[] audience;
	
	@JsonProperty("typ")
	private String type;
	
	@JsonProperty("azp")
	private String authorizedParty;
	
	// USER PROPERTIES
	@JsonProperty("sid")
	private String sessionId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("preferred_username")
	private String preferred_username;
}
