package br.com.acqua.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Produto;
import br.com.acqua.entity.User;
import br.com.acqua.service.MovimentacaoService;
import br.com.acqua.service.ProdutoService;
import br.com.acqua.service.UsuarioService;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

	private static final String CADASTRO_VIEW = "movimentacao/movimentacao-cadastro";
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@GetMapping("/novo")
	public ModelAndView novo(){
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new Movimentacao());
		view.addObject(new Produto());
		return view;
	}
	
	@RequestMapping( method = RequestMethod.POST)
	public String salvar(@Validated Movimentacao movimentacao, Errors errors, RedirectAttributes attributes, ModelMap model) {

		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {
			
			System.out.println("p"+movimentacao.getProduto());
			
			User user = new User();
			
			user.setCodigo("sasasas");
			user.setEnabled(true);
			user.setNome("Geovane");
			user.setPassword("'123");
			user.setSobrenome("freitas");
			user.setUsername("fc");
			
			movimentacao.setUser(user);
			//movimentacao.setProduto();

			movimentacaoService.salvar(movimentacao);
			
			

			attributes.addFlashAttribute("mensagem", "Movimentação salva com Sucesso!");

			return "redirect:/movimentacaos";

		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataCadastro", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}


	@GetMapping("/produtoPorCodigo/{codigo}")
public String obterProdutoPorCodigo(@PathVariable String codigo, ModelMap model) {
		

		
		model.addAttribute("produto", produtoService.findByCodigo(codigo));
	
	return "movimentacao/movimentacao-cadastro";
}

}
