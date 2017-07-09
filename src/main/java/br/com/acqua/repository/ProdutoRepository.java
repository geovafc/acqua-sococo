package br.com.acqua.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;


/**
 * Spring Data JPA repository for the Produto entity.
 */
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
	
	Produto findByAvatar(AvatarProd avatar);
	
	Produto findByCodigoDeBarras(String codigo_barras);

}
