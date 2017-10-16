package br.com.acqua.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
	public AvatarProd saveOrUpdate(AvatarProd avatar) {
		return avatarRepository.save(avatar);
	}
	
	public AvatarProd buscarPorFile(byte[] avatar){
		return avatarRepository.findByAvatar(avatar);
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
		}else{
			try {
				avatar.setTipo("image/png");
				avatar.setTitulo("default.png");
				avatar.setAvatar(this.imageToByte());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return avatar;
	}

	public AvatarProd findById(Long id) {
		return avatarRepository.findOne(id);
	}
	
	public byte[] imageToByte() throws IOException {
	    InputStream is = null;
	    byte[] buffer = null;
	    is = new FileInputStream("src/main/resources/static/imagens/default.png");
	    buffer = new byte[is.available()];
	    is.read(buffer);
	    is.close();
	    return buffer;
	}

}
