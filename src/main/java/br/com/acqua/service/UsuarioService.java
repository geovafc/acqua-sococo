package br.com.acqua.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	public Page<Usuario> findByPagination(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return usuarioRepository.findAllByOrderByIdAsc(pageable);
	}

	public void salvar(Usuario usuario) {
		try {
			Usuario usuarioSalvo = null;
			if (usuario.getId() != null ){
				usuarioSalvo = usuarioRepository.findOne(usuario.getId());

			}
			
			if (usuario.getId() == null){
				System.out.println("CRIPT SENHA");
				String hash = new BCryptPasswordEncoder().encode(usuario.getPassword());
				usuario.setPassword(hash);
				
			} 

			adcPermissoesUsuario(usuario);
			
//Metodo para salvar a senha do usuario caso ela venha vazia da view

			if (usuarioSalvo != null && usuario.getPassword().isEmpty()){
				usuario.setPassword(usuarioSalvo.getPassword());
			}
			
			

			
			usuarioRepository.save(usuario);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Algo deu errado ao salvar este operador");
		}
	}
	
	public void atualizarSenha(Usuario usuario) {
		try {
			Usuario usuarioSalvo = usuarioRepository.findByUsername(usuario.getUsername());
			
			System.out.println("senha "+usuario.getPassword());
			System.out.println("senha 2"+usuarioSalvo.getPassword());

			
			if (!usuario.getPassword().isEmpty()){
				String hash = new BCryptPasswordEncoder().encode(usuario.getPassword());
				usuarioSalvo.setPassword(hash);
			}	
			


			usuarioRepository.save(usuarioSalvo);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Algo deu errado ao atualizar a senha do operador");
		}
	}

	private void adcPermissoesUsuario(Usuario usuario) {
		List<Permissao> permissaos = new ArrayList<>();


		if (usuario.getId() == null) {

			if (usuario.getDataCadastro() == null) {
				usuario.setDataCadastro(Date.valueOf(LocalDate.now()));
			}
		} 
		
		permissaos.add(permissaoRepository.findByNome(usuario.getPerfil()));
		

		usuario.setPermissaos(permissaos);
	}

	public void delete(Long id) {
		usuarioRepository.delete(id);
	}

	public Usuario findById(Long id) {
		return usuarioRepository.findOne(id);
	}
	
	public Usuario findByUserName(String userName){
		return usuarioRepository.findByUsername(userName);
	}
}
