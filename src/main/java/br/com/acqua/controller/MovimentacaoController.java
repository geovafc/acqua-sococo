package br.com.acqua.controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Produto;
import br.com.acqua.entity.User;
import br.com.acqua.repository.filter.ProdutoFilter;
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
	private UsuarioService userService;

	ModelAndView view;

	@GetMapping
	public ModelAndView listar() {

		this.view = new ModelAndView("movimentacao/movimentacoes");

		List<Movimentacao> movimentacoes = movimentacaoService.listar();

		this.view.addObject("movimentacoes", movimentacoes);

		return view;
	}

	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(@ModelAttribute("filtro") ProdutoFilter filtro) {

		this.view = new ModelAndView("movimentacao/consultar-produto");

		return view;
	}

	@GetMapping("/novo")
	public ModelAndView novo() {
		this.view = new ModelAndView(CADASTRO_VIEW);
		Produto produto = new Produto();
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setProduto(produto);
		view.addObject("movimentacao", movimentacao);
		view.addObject("produto", produto);
		return view;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Movimentacao movimentacao, RedirectAttributes attributes) throws Exception {

		try {
//Apos implementacao de login, obter usuario da sessao
			movimentacao.setUser(userService.findById(new Long(1)));

			movimentacaoService.salvar(movimentacao);

			attributes.addFlashAttribute("mensagem", "Movimentação salva com Sucesso!");

			return "redirect:/movimentacoes";

		} catch (IllegalArgumentException e) {
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping(value = {"/{id}"} , method = {RequestMethod.GET})
	public ModelAndView editar(@PathVariable("id") Optional<Long> id, @ModelAttribute("movimentacao") Movimentacao movimentacao) {
		
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(id.isPresent()){
			movimentacao = movimentacaoService.buscar(id.get());
			mv.addObject("movimentacao", movimentacao);
			
			System.out.println("OBJETO mov editar " + movimentacao.getProduto().getCodigoDeBarras());
			
			
		}
		
		return mv;
		
	}
	
	@DeleteMapping(value="{id}")
	private String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		movimentacaoService.excluir(id);
		attributes.addFlashAttribute("mensagem", "Movimentação excluída com sucesso!");	
		return "redirect:/movimentacoes";
	}

	@GetMapping("/pesquisar/codigo/{codigo}")
	public ModelAndView pesquisarProdutoPorCodigo(@PathVariable String codigo) {

		this.view = new ModelAndView("movimentacao/movimentacao-cadastro-beta");

		ProdutoFilter filtro = new ProdutoFilter();
		filtro.setCodigo(codigo);

		if (filtro.getCodigo() == "") {
			return this.pesquisar(filtro);
		}
		try {
			Produto produto = new Produto();
			produto = produtoService.findByCodigo(filtro);

			if (produto == null) {
				return this.pesquisar(filtro);
			}

			Movimentacao movimentacao = new Movimentacao();
			movimentacao.setDataHora(new Date(System.currentTimeMillis()));
			movimentacao.setProduto(produto);

			view.addObject(movimentacao);

			return view;

		} catch (Exception e) {
			return this.pesquisar(filtro);
		}

	}

}
