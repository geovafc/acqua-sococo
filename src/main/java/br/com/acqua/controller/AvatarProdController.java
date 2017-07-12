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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.service.AvatarProdService;

@Controller
@RequestMapping("/avatar-prod")
public class AvatarProdController {
	
	@Autowired
	private AvatarProdService avatarService;
	
	@GetMapping("/update/{id}")
	public ModelAndView preUpdate(@PathVariable("id") Long id,
			@ModelAttribute("avatar") AvatarProd avatar) {
		ModelAndView view = new ModelAndView("avatar/atualizar");
		
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
