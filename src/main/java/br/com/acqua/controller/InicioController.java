
package br.com.acqua.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Produto;
import br.com.acqua.entity.Usuario;
import br.com.acqua.service.MovimentacaoService;
import br.com.acqua.service.ProdutoService;

@Controller
public class InicioController {

	@Autowired
	private MovimentacaoService movimentacaoService;

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/")
	public String inicio() {
		return "redirect:/index";
	}

	@GetMapping("/index")
	public ModelAndView indexAcqua() {

		ModelAndView view = new ModelAndView("index");

		List<Movimentacao> movimentacoes = movimentacaoService.listar();
		List<Produto> produtos = produtoService.findAll();
		Usuario usuario = new Usuario();	

		int quantidadeMovimentacao = movimentacaoService.findAll().size();
		int quantidadeProdutos = 0;

		quantidadeProdutos = produtoService.findEnabled().size();
		
		view.addObject("movimentacoes", movimentacoes);
		view.addObject("QTDmovimentacoes", quantidadeMovimentacao);
		view.addObject("QTDprodutos", quantidadeProdutos);
		view.addObject("usuario", usuario);	

		return view;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}

}