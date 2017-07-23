package br.com.acqua.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.service.AvatarProdService;
import br.com.acqua.service.ProdutoService;

@Controller
@RequestMapping("/avatar-prod")
public class AvatarProdController {

	@Autowired
	private AvatarProdService avatarService;

	@Autowired
	private ProdutoService produtoService;

	@PostMapping("/update")
	public String update(@ModelAttribute("avatar") AvatarProd avatar, @RequestParam("file") MultipartFile file) {

		Long id = avatar.getId();

		avatar = avatarService.getAvatarByUpload(file);
		avatar.setId(id);
		avatarService.saveOrUpdate(avatar);

		return "redirect:/produto/perfil/" + produtoService.findByAvatar(avatar).getId();
	}

	@GetMapping("/update/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Long id, @ModelAttribute("avatar") AvatarProd avatar) {

		ModelAndView view = new ModelAndView("avatar/avatar-atualizar");

		view.addObject("id", id);

		return view;
	}

	@GetMapping("/load/{id}")
	public ResponseEntity<byte[]> loadAvatar(@PathVariable("id") Long id) {

		AvatarProd avatar = avatarService.findById(id);

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.valueOf(avatar.getTipo()));

		InputStream is = new ByteArrayInputStream(avatar.getAvatar());

		try {
			return new ResponseEntity<>(IOUtils.toByteArray(is), headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
