package br.com.acqua.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.acqua.entity.User;
import br.com.acqua.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final String CADASTRO_VIEW = "usuario/usuario-cadastro";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/novo")
	public ModelAndView novo(){
		
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new User());
		return view;
	}
	
	@GetMapping
	public ModelAndView listar(){
		ModelAndView view = new ModelAndView("usuario/usuarios");
		List<User> usuarios = usuarioService.listar();
		view.addObject("usuarios", usuarios);
		return view;
	}
	
	
}
