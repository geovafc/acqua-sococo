package br.com.acqua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.acqua.entity.Movimentacao;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

	private static final String CADASTRO_VIEW = "movimentacao/movimentacao-cadastro";
	
	@GetMapping("/novo")
	public ModelAndView novo(){
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new Movimentacao());
		return view;
	}
}
