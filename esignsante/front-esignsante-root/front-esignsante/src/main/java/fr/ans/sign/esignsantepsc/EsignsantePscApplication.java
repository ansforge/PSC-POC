/**
 * (c) Copyright 1998-2021, ANS. All rights reserved.
 */
package fr.ans.sign.esignsantepsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
@ComponentScan(basePackages = {"fr.ans.api.sign.esignsante.psc"})

public class EsignsantePscApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsignsantePscApplication.class, args);
	}
}
