package br.com.acqua.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
			usuario.setRoles(UserRole.USER);	
			usuarioService.salvar(usuario);
			attributes.addFlashAttribute("mensagem", "Operador cadastrado com sucesso!");
			return "redirect:/usuarios";
		}catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("mensagem", "Desculpe, mas algo deu errado.");
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping(value = {"/{id}"} , method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editar(@PathVariable("id") Optional<Long> id, @ModelAttribute("usuario") User usuario) {
		
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(id.isPresent()){
			usuario = usuarioService.findById(id.get());
			mv.addObject("usuario", usuario);
			
			System.out.println("OBJETO " + usuario.getNome());
			
			
		}
		
		return mv;
		
	}
	
	@DeleteMapping(value="{id}")
	private String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		usuarioService.delete(id);
		attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");	
		return "redirect:/usuarios";
	}
	
}
