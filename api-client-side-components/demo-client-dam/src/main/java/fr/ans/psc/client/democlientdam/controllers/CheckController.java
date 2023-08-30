/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {
	    @GetMapping("/check")
	    public String check() {
	        return "demo-client-dam webapp is alive";
	    }
	}
