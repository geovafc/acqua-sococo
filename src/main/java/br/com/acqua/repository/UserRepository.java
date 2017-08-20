package br.com.acqua.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByNome(String nome);

}
