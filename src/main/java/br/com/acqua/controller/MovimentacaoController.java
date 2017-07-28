package br.com.acqua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Produto;
import br.com.acqua.service.MovimentacaoService;
import br.com.acqua.service.ProdutoService;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

	private static final String CADASTRO_VIEW = "movimentacao/movimentacao-cadastro";
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	Movimentacao movimentacao;
	
	ModelAndView view;
	
	
	
	@GetMapping("/novo")
	public ModelAndView novo(){
		view = new ModelAndView(CADASTRO_VIEW);
		System.out.println("novo ");
		movimentacao =  new Movimentacao();
		movimentacao.setProduto(new Produto());
		view.addObject("movimentacao",movimentacao);
		view.addObject(new Produto());
		return view;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Movimentacao movimentacao, Errors errors, RedirectAttributes attributes) {
		
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {
			
			System.out.println("movimentacao: "+movimentacao);

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
		
		
		//Movimentacao movimentacao = new Movimentacao();
		
		Produto produto = produtoService.findByCodigo(codigo);
		movimentacao =  new Movimentacao();
		movimentacao.setProduto(produto);
		
		System.out.println("nome digitado: "+movimentacao.getProduto().getNome());
		//view.addObject("movimentacao", movimentacao );
		
		//model.addAttribute("produto", produtoService.findByCodigo(codigo));
		//view.addObject("produto",produto);
		//model.addAttribute("produto", produto);
		//model.addAttribute("movimentacao", movimentacao);
		model.addAttribute("movimentacao", movimentacao);
	
	
		return view.getViewName();
	}

}
