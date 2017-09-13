package br.com.acqua.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByNome(String nome);
	public Usuario findByUsername(String userName);
	
	Page<Usuario> findAllByOrderByIdAsc(Pageable pageable);

}
