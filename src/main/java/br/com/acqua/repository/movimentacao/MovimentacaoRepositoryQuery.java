package br.com.acqua.repository.movimentacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.repository.filter.MovimentacaoFilter;

public interface MovimentacaoRepositoryQuery {
	
	public Page<Movimentacao> filtrar(MovimentacaoFilter movimentacaoFilter, Pageable pageable);

}
