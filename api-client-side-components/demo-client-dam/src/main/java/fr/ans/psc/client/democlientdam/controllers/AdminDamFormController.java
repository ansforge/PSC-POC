package fr.ans.psc.client.democlientdam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.ans.psc.client.democlientdam.calls.ApiCalls;

import org.springframework.ui.Model;

@Controller
public class AdminDamFormController {
	
	 @Autowired
    private ApiCalls api;

	@GetMapping("/admin/view/form")
	public String getFormDam(Model model) {

		// TODO 
		return "form-dam";
	}
}
