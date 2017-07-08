package br.com.acqua;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {
	
	

}
