package br.com.acqua.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.acqua.entity.Produto;
import br.com.acqua.repository.ProdutosRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutosRepository produtosRepository;
	
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

	public List<Produto> listar() {
		return produtosRepository.findAll();
	}
	
	public void excluir(Long id) {
		produtosRepository.delete(id);
	}
	
	public Produto buscar(Long id){
		return produtosRepository.findOne(id);
	}
	
	public Produto buscarPorCodigo(String codigoDeBarras){
		return produtosRepository.findByCodigoDeBarras(codigoDeBarras);
	}

}
