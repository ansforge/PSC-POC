package fr.ans.psc.client.democlientdam.calls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;


@Getter
@Configuration
public class DemoClientConfiguration {
	
	@Value("${dam.api.url}")
	private String damReaderBaseUrl;

	@Value("${dam.api.key}")
	private String damApiKey;
}
