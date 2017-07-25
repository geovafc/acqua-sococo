package br.com.acqua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Produto findByCodigoDeBarras(String codigoDeBarras);
	
	Produto findByAvatar(AvatarProd avatar);

	@Modifying
	@Query("update Produto p set p.nome = ?1, p.descricao = ?2, p.codigoDeBarras = ?3 where p.id = ?4")
	public void updateNomeAndDescricaoAndCodigoBarra(String nome, String descricao, String codigoDeBarras, Long id);

}
