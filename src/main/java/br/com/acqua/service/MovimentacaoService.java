package br.com.acqua.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	
	public void salvar(Movimentacao movimentacao) {

		try {
			movimentacaoRepository.save(movimentacao);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		} 
	}

	public List<Movimentacao> listar() {
		return movimentacaoRepository.findAll();
	}
	
	public void excluir(Long id) {
		movimentacaoRepository.delete(id);
	}
	
	public Movimentacao buscar(Long id){
		return movimentacaoRepository.findOne(id);
	}

}
