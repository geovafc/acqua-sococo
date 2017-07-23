package br.com.acqua.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;
import br.com.acqua.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtosRepository;
	
	public void salvar(Produto produto) {

		try {
			if (produto.getId() == null) {
				produto.setDataCadastro(Date.valueOf(LocalDate.now()));
			}
			produtosRepository.save(produto);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		} 
	}

	public List<Produto> findAll() {
		return produtosRepository.findAll();
	}
	
	public void delete(Long id) {
		produtosRepository.delete(id);
	}
	
	public Produto findById(Long id){
		return produtosRepository.findOne(id);
	}
	
	public Produto findByCodigo(String codigoDeBarras){
		return produtosRepository.findByCodigoDeBarras(codigoDeBarras);
	}

	@Transactional(readOnly = false)
	public void updateNomeAndDescricaoAndCodigoBarra(Produto produto) {
		System.out.println(produto.getNome()+produto.getDescricao()+produto.getCodigoDeBarras()+ produto.getId());
		produtosRepository.updateNomeAndDescricaoAndCodigoBarra(produto.getNome(), produto.getDescricao(), produto.getCodigoDeBarras(), produto.getId());
	}

	public Produto findByAvatar(AvatarProd avatar) {
		return produtosRepository.findByAvatar(avatar);
	}

}
