package br.com.acqua.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;
import br.com.acqua.service.AvatarProdService;
import br.com.acqua.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

	private static final String CADASTRO_VIEW = "produto/produto-cadastro";

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private AvatarProdService avatarService;

	@GetMapping("/novo")
	public ModelAndView novo(@ModelAttribute("avatar") AvatarProd avatar) {
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		view.addObject(new Produto());
		return view;
	}

	@GetMapping
	public ModelAndView listar() {
		ModelAndView view = new ModelAndView("produto/produtos");
		List<Produto> produtos = produtoService.findAll();
		view.addObject("produtos", produtos);
		return view;
	}

	@PostMapping
	public String salvar(@Validated Produto produto, Errors erros, RedirectAttributes attributes,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		AvatarProd avatar = new AvatarProd();
		if (erros.hasErrors()) {
			return CADASTRO_VIEW;
		}
			avatar = avatarService.getAvatarByUpload(file);
			produto.setAvatar(avatar);

		try {
			produtoService.salvar(produto);
			attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
			return "redirect:/produto";

		} catch (IllegalArgumentException e) {
			erros.rejectValue("dataCadastro", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@GetMapping("/perfil/{id}")
	public ModelAndView perfil(@PathVariable("id") Produto produto){
		ModelAndView view = new ModelAndView();
		view.setViewName("produto/produto-perfil");
		view.addObject("produto",produto);
		return view;
	}

	@RequestMapping(value = {"/update/{id}", "/update"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView editar(@PathVariable("id") Optional<Long> id, @ModelAttribute("produto") Produto produto) {
		ModelAndView view = new ModelAndView();
		if (id.isPresent()) {
			produto = produtoService.findById(id.get());
			view.addObject("produto", produto);
			view.setViewName("produto/produto-atualizar");
			return view;
		}
		
		produtoService.updateNomeAndDescricaoAndCodigoBarra(produto);
		
		view.setViewName("redirect:/produto/perfil/" + produto.getId());
		
		return view;
		
	}
	
	@DeleteMapping("{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
		produtoService.delete(id);
		attributes.addFlashAttribute("mensagem", "Produto excluido com sucesso!");
		return "redirect:/produto";
	}

}
