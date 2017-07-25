package br.com.acqua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.Movimentacao;
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
	
	
	
	@GetMapping("/novo")
	public ModelAndView novo(){
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new Movimentacao());
		return view;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String salvar(@Validated Movimentacao movimentacao, Errors errors, RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {

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
		
		System.out.println("codigo digitado: "+codigo);
		model.addAttribute("produto", produtoService.findByCodigo(codigo));
	
	
	return "";
}

}
