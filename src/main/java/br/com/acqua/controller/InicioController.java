package br.com.acqua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Jairo Sousa
 * 06/01/2017
 */

@Controller
@RequestMapping("/")
public class InicioController {

	@GetMapping
	public String inicio() {
		return "index";
	}

}
