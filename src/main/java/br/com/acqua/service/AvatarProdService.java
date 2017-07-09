package br.com.acqua.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.repository.AvatarProdRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class AvatarProdService {

	@Autowired
	private AvatarProdRepository avatarRepository;

	@Transactional(readOnly = false)
	public AvatarProd save(AvatarProd avatar) {
		return avatarRepository.save(avatar);
	}

	public AvatarProd getAvatarByUpload(MultipartFile file) {
		AvatarProd avatar = new AvatarProd();

		if (file != null && file.getSize() > 0) {
			try {
				avatar.setTitulo(file.getOriginalFilename());
				avatar.setTipo(file.getContentType());
				avatar.setAvatar(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return avatar;
	}

	public AvatarProd findById(Long id) {
		return avatarRepository.findOne(id);
	}

}
