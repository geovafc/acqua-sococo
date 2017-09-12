package br.com.acqua.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.acqua.entity.Permissao;
import br.com.acqua.entity.Usuario;
import br.com.acqua.repository.PermissaoRepository;
import br.com.acqua.repository.UserRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UserRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}
	
	
	public void salvar(Usuario usuario){
		try{
			
			List<Permissao> permissaos = new ArrayList<>();
			
			permissaos.add(permissaoRepository.findOne(2L));
			
			usuario.setPermissaos(permissaos);
			
			usuario.setEnabled(true);
			
			usuarioRepository.save(usuario);	
			
		}catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException ("Algo deu errado ao salvar este operador");
		}
	}
	
	public void delete(Long id) {
		usuarioRepository.delete(id);
	}
	
	public Usuario findById(Long id){
		return usuarioRepository.findOne(id);
	}
}
