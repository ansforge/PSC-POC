package fr.ans.psc.client.democlientdam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeaderToken {

	@JsonProperty("alg")
	private String algo;
	
	@JsonProperty("typ")
	private String type;
	
	@JsonProperty("kid")
	private String keyId;
}
