package br.com.acqua.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.Movimentacao;


/**
 * Spring Data JPA repository for the Movimentacao entity.
 */

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {

	Page<Movimentacao> findAllByOrderByIdAsc(Pageable pageable);
	
	// Buscar onde a data cadastro está dentro de um período.
    Page<Movimentacao> findByDataHoraBetween(Date inicio, Date fim, Pageable pageable);
}
