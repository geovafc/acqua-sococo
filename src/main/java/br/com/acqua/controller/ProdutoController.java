package br.com.acqua.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.ProdutoService;
import br.com.acqua.entity.Produto;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private static final String CADASTRO_VIEW = "produto/produto-cadastro";

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new Produto());
		return view;
	}

	@GetMapping
	public ModelAndView listar() {
		ModelAndView view = new ModelAndView("produto/produtos");
		List<Produto> produtos = produtoService.listar();
		view.addObject("produtos", produtos);
		return view;
	}

	@PostMapping
	public String salvar(@Validated Produto produto, Errors erros, RedirectAttributes attributes) {

		if (erros.hasErrors()) {
			return CADASTRO_VIEW;
		}

		try {
			produtoService.salvar(produto);
			attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
			return "redirect:/produtos/novo";
		} catch (IllegalArgumentException e) {
			erros.rejectValue("dataCadastro", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}

	@GetMapping("{id}")
	public ModelAndView editar(@PathVariable("id") Produto produto) {
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(produto);
		return view;
	}

	@DeleteMapping("{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
		produtoService.excluir(id);
		attributes.addFlashAttribute("mensagem", "Produto excluido com sucesso!");
		return "redirect:/produtos";
	}

}
