/**
 * (c) Copyright 2023, ANS. All rights reserved.
 */
package fr.ans.psc.client.democlientdam.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.ans.psc.client.democlientdam.calls.ApiCalls;
import fr.ans.psc.client.democlientdam.exception.ApiCallException;
import fr.ans.psc.client.democlientdam.tools.Helper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

@Controller
@Slf4j
public class Logout {



	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, ApiCallException {

	
		return "logout";
	}


	
}
