package br.com.acqua.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.dto.MovimentacaoMesAnoDTO;
import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Produto;
import br.com.acqua.entity.paginator.Pager;
import br.com.acqua.repository.filter.MovimentacaoFilter;
import br.com.acqua.repository.filter.ProdutoFilter;
import br.com.acqua.service.MovimentacaoService;
import br.com.acqua.service.ProdutoService;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {
	
	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 5;
	private static final int[] PAGE_SIZES = { 5, 10, 20 };

	private static final String CADASTRO_VIEW = "movimentacao/movimentacao-cadastro";
	private static final String DETALHES_VIEW = "movimentacao/movimentacao-detalhes";
	private static final String CADASTRO_LISTVIEW = "movimentacao/movimentacoes";


	@Autowired
	private MovimentacaoService movimentacaoService;

	@Autowired
	private ProdutoService produtoService;

	ModelAndView view;
	
	@GetMapping
	public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
			@ModelAttribute("filtro") MovimentacaoFilter filtro, @RequestParam("page") Optional<Integer> page) {
		ModelAndView modelAndView = new ModelAndView(CADASTRO_LISTVIEW);

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);

		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Movimentacao> movimentacoes = movimentacaoService.findByPagination(evalPage, evalPageSize);
		Pager pager = new Pager(movimentacoes.getTotalPages(), movimentacoes.getNumber(), BUTTONS_TO_SHOW);

		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("selectedPageSize", evalPageSize);
		modelAndView.addObject("pageSizes", PAGE_SIZES);
		modelAndView.addObject("pager", pager);
		return modelAndView;
	}

	@GetMapping("/pesquisar/periodo")
	public ModelAndView pesquisarPorPeriodo(@ModelAttribute("filtro") MovimentacaoFilter filtro,
			@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Throwable {
				
		ModelAndView modelAndView = new ModelAndView("movimentacao/movimentacoes");

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);

		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Movimentacao> movimentacoes = movimentacaoService.findByDataHoraBetween(filtro, evalPage, evalPageSize);
		
		Pager pager = new Pager(movimentacoes.getTotalPages(), movimentacoes.getNumber(), BUTTONS_TO_SHOW);

		modelAndView.addObject("movimentacoes", movimentacoes);
		modelAndView.addObject("selectedPageSize", evalPageSize);
		modelAndView.addObject("pageSizes", PAGE_SIZES);
		modelAndView.addObject("pager", pager);
		return modelAndView;
	}
	
	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(@ModelAttribute("filtro") ProdutoFilter filtro) {

		this.view = new ModelAndView("movimentacao/consultar-produto");

		return view;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Movimentacao movimentacao, RedirectAttributes attributes) throws Exception {

		try {

			String usernName = SecurityContextHolder.getContext().getAuthentication().getName();

			movimentacaoService.salvar(movimentacao, usernName);

			attributes.addFlashAttribute("mensagem", "Movimentação salva com Sucesso!");

			return "redirect:/movimentacoes";

		} catch (IllegalArgumentException e) {
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping(value = {"/{id}"} , method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editar(@PathVariable("id") Optional<Long> id, @ModelAttribute("movimentacao") Movimentacao movimentacao) {
		
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(id.isPresent()){
			movimentacao = movimentacaoService.buscar(id.get());
			mv.addObject("movimentacao", movimentacao);
			
			System.out.println("OBJETO " +movimentacao);
			
			
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = {"/detalhes/{id}"} , method = {RequestMethod.GET})
	public ModelAndView detalhes(@PathVariable("id") Optional<Long> id, @ModelAttribute("movimentacao") Movimentacao movimentacao) {
		
		
		ModelAndView mv = new ModelAndView(DETALHES_VIEW);
		if(id.isPresent()){
			movimentacao = movimentacaoService.buscar(id.get());
			mv.addObject("movimentacao", movimentacao);
			
			System.out.println("OBJETO " +movimentacao);
			
			
		}
		
		return mv;
		
	}
	
	@DeleteMapping(value="{id}")
	 	private String excluir(@PathVariable Long id, RedirectAttributes attributes) {
	 		
	 		movimentacaoService.excluir(id);
	 		attributes.addFlashAttribute("mensagem", "Movimentação excluída com sucesso!");	
	 		return "redirect:/movimentacoes";
		}
	
	@GetMapping("/countMesAno")
	public ResponseEntity<List<MovimentacaoMesAnoDTO>> getCountMovimentacoesByMesAno(){
		//ModelAndView view = new ModelAndView("index");
		
		
		//view.addObject("countMovimentacoesByMesAno", movimentacaoService.getCountMovimentacoesByMesAno() );
			return ResponseEntity.status(HttpStatus.OK).body(movimentacaoService.getCountMovimentacoesByMesAno()) ;
	}
	
	
	@GetMapping("/pesquisar/codigo")
	public ModelAndView pesquisarProdutoPorCodigo(@ModelAttribute("filtro") ProdutoFilter filtro) {

		this.view = new ModelAndView(CADASTRO_VIEW);
		
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