package br.com.acqua.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.acqua.entity.Movimentacao;


/**
 * Spring Data JPA repository for the Movimentacao entity.
 */

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {

	Page<Movimentacao> findAllByOrderByIdAsc(Pageable pageable);
	//Falta fazer para contar po ano
	@Query("select count(m) from Movimentacao m where month(m.dataHora) = :dataMovimentacao")
	Long countByMovimentacoesFromMesAno(@Param("dataMovimentacao") Integer dataMovimentacao);
}
