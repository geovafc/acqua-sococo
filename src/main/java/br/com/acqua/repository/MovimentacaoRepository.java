package br.com.acqua.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Produto;
import br.com.acqua.repository.movimentacao.MovimentacaoRepositoryQuery;


/**
 * Spring Data JPA repository for the Movimentacao entity.
 */

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long>, MovimentacaoRepositoryQuery {

	Page<Movimentacao> findAllByOrderByIdDesc(Pageable pageable);
	//Falta fazer para contar po ano
	@Query("select count(m) from Movimentacao m where month(m.dataHora) = :dataMovimentacao and year(m.dataHora) = :anoMovimentacao")
	Long countByMovimentacoesFromMesAno(@Param("dataMovimentacao") Integer dataMovimentacao, @Param("anoMovimentacao") Integer anoMovimentacao);
	
	// Buscar onde a data cadastro está dentro de um período.
    Page<Movimentacao> findByDataHoraBetween(Date inicio, Date fim, Pageable pageable);
    
    Page<Movimentacao> findByDataHoraBetweenAndProduto(Date inicio, Date fim,Produto produto, Pageable pageable);
}
