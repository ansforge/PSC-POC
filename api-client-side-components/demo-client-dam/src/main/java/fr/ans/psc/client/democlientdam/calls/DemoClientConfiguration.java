/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.calls;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Getter
@Configuration
public class DemoClientConfiguration {

	@Value("${dam.api.path}")
	private String damReaderPath;

	@Value("${client.poc.keystore.location}")
	private String keystoreLocation;

	@Value("${client.poc.keystore.password}")
	private String keystorePassword;

	@Value("${client.poc.truststore.location}")
	private String truststoreLocation;

	@Bean
	public RestTemplate restTemplate() throws IOException, GeneralSecurityException {

		KeyStore ks = loadKeyStore(keystoreLocation, keystorePassword);
		KeyStore ts = loadKeyStore(truststoreLocation, null);
		SSLContext sslContext = new SSLContextBuilder().loadKeyMaterial(ks, keystorePassword.toCharArray())
				.loadTrustMaterial(ts, (TrustStrategy) (chain, authType) -> true).build();
		SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConFactory).build();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(requestFactory);
	}

	private KeyStore loadKeyStore(String location, String password) throws IOException, GeneralSecurityException {
		InputStream in = null;
		try {
			in = new FileInputStream(location);
			final KeyStore keyStore = KeyStore.getInstance("JKS");
			if (password == null) {
				keyStore.load(in, null);
			} else {
				keyStore.load(in, password.toCharArray());
			}
			return keyStore;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
}
