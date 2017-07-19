package br.com.acqua.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.User;
import br.com.acqua.entity.UserRole;
import br.com.acqua.service.UsuarioService;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final String CADASTRO_VIEW = "usuario/usuario-cadastro";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/novo")
	public ModelAndView novo(){
		User usuario = new User();
		/*usuario.setCodigo("001");
		
		try {
			String date_s = "2011-01-18"; 
			SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd"); 
			Date date = dt.parse(date_s);
			usuario.setDataCadastro(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		usuario.setEnabled(true);
		usuario.setNome("teste");
		usuario.setPassword("234");
		usuario.setSobrenome("teste");
		usuario.setUsername("rraa");
		
		usuarioService.salvar(usuario);*/
		
		
		
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject("usuario", usuario);
		
		
		
		return view;
		
	
		
		
	}
	
	@GetMapping
	public ModelAndView listar(){
		ModelAndView view = new ModelAndView("usuario/usuarios");
		List<User> usuarios = usuarioService.listar();
		view.addObject("usuarios", usuarios);
		return view;
	}
	
	
	@ModelAttribute("todosPerfis")
	public List<UserRole> todosStatusTitulo() {
		return Arrays.asList(UserRole.values());
	}
	
	@RequestMapping(method = RequestMethod.POST )
	public String salvar(@Validated User usuario, Errors errors, RedirectAttributes attributes  ){
		
		if(errors.hasErrors()){
			return CADASTRO_VIEW;
		}
		
		try{
			
			Date hoje = new Date();
			usuario.setDataCadastro(hoje);
				
			usuarioService.salvar(usuario);
			attributes.addFlashAttribute("mensagem", "Operador cadastrado com sucesso!");
			return "redirect:/usuarios";
		}catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("mensagem", "Desculpe, mas algo deu errado.");
			return CADASTRO_VIEW;
		}
	}
	
}
