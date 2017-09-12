package br.com.acqua.repository;

import br.com.acqua.entity.Movimentacao;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Movimentacao entity.
 */

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {

}
