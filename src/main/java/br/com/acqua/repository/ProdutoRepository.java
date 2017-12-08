package br.com.acqua.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	
	Page<Produto> findAllByOrderByIdAsc(Pageable pageable);
	
	Page<Produto> findByEnabledOrderByIdDesc(boolean enaled, Pageable pageable);
	
	List<Produto> findByEnabledOrderByIdDesc(boolean enaled);
	
	Produto findByCodigoDeBarras(String codigoDeBarras);
	
	Produto findByAvatar(AvatarProd avatar);

	@Modifying
	@Query("update Produto p set p.nome = ?1, p.descricao = ?2, p.codigoDeBarras = ?3 where p.id = ?4")
	public void updateNomeAndDescricaoAndCodigoBarra(String nome, String descricao, String codigoDeBarras, Long id);

	@Transactional
	@Modifying
	@Query("update Produto p set p.enabled = ?1 where p.id = ?2")
	public void updateEnable(boolean enabled, Long id);
}
