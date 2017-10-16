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
			
			List<Permissao> permissaos = new ArrayList<>();


			if (usuario.getId() == null) {

				permissaos.add(permissaoRepository.findByNome("ROLE_USER"));

				if (usuario.getDataCadastro() == null) {
					usuario.setDataCadastro(Date.valueOf(LocalDate.now()));
					usuario.setEnabled(Boolean.TRUE);
				}

			} else {
				
				System.out.println(usuarioRepository.findByUsername(usuario.getUsername()).getPermissoes().size());
				permissaos.addAll(usuarioRepository.findByUsername(usuario.getUsername()).getPermissoes());

			}
			
			usuario.setEnabled(Boolean.TRUE);

			usuario.setPermissaos(permissaos);
			
			String hash = new BCryptPasswordEncoder().encode(usuario.getPassword());

			usuario.setPassword(hash);

			usuarioRepository.save(usuario);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Algo deu errado ao salvar este operador");
		}
	}

	public void delete(Long id) {
		usuarioRepository.delete(id);
	}

	public Usuario findById(Long id) {
		return usuarioRepository.findOne(id);
	}
}
