package br.com.acqua.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.acqua.entity.User;
import br.com.acqua.repository.UserRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UserRepository usuarioRepository;
	
	public List<User> listar() {
		return usuarioRepository.findAll();
	}
	
	
	public void salvar(User usuario){
		try{			
			usuarioRepository.save(usuario);			
		}catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException ("Algo deu errado ao salvar este operador");
		}
	}
}
