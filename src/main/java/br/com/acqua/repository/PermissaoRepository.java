package br.com.acqua.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
