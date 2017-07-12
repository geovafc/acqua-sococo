package br.com.acqua.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;
import br.com.acqua.service.AvatarProdService;
import br.com.acqua.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	private static final String CADASTRO_VIEW = "produto/produto-cadastro";

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private AvatarProdService avatarService;

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
	public String salvar(@Validated Produto produto, 
			Errors erros, RedirectAttributes attributes,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		if (erros.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		if (produto.getAvatar() == null && file != null) {
			AvatarProd avatar = avatarService.getAvatarByUpload(file);
			produto.setAvatar(avatar);
		}else if (produto.getAvatar() != null && file != null){
			AvatarProd avatar = avatarService.getAvatarByUpload(file);
			avatar.setId(produto.getAvatar().getId());
			produto.setAvatar(avatarService.save(avatar));
		}

		try {
			produtoService.salvar(produto);
			attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
			return "redirect:/produtos";
			
		} catch (IllegalArgumentException e) {
			erros.rejectValue("dataCadastro", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}

	@GetMapping("{id}")
	public ModelAndView editar(@PathVariable("id") Produto produto) throws IOException {
		ModelAndView view = new ModelAndView(CADASTRO_VIEW);
		
		InputStream is = new ByteArrayInputStream(produto.getAvatar().getAvatar());
		
		byte[] file = IOUtils.toByteArray(is);
		
		view.addObject("file", file);
		
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
