package br.com.acqua.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Usuario;
import br.com.acqua.repository.MovimentacaoRepository;
import br.com.acqua.repository.UserRepository;
import br.com.acqua.repository.filter.MovimentacaoFilter;

@Service
public class MovimentacaoService {

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private UserRepository userRepository;

	public void salvar(Movimentacao movimentacao, String userName) {

		Usuario user = userRepository.findByUsername(userName);

		try {
			movimentacao.setUsuario(user);
			movimentacao.setDataHora(new Date(System.currentTimeMillis()));
			movimentacaoRepository.save(movimentacao);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public Page<Movimentacao> findByPagination(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return movimentacaoRepository.findAllByOrderByIdAsc(pageable);
	}

	public Page<Movimentacao> findByDataHoraBetween(MovimentacaoFilter filter, int page, int size) {
		
		Pageable pageable = new PageRequest(page, size);
		
		System.out.println(filter.getInicio());

//		Date inicio = new Date(filter.getInicio().getYear(),filter.getInicio().getMonth(), filter.getInicio().getDay() );
//
//		Date fim = new Date(filter.getFim().getYear(), filter.getFim().getMonth(), filter.getFim().getDay());

//		System.out.println("Inicio: " + inicio);
//		System.out.println("Fim: " + fim);
		
		return movimentacaoRepository.findByDataHoraBetween(filter.getInicio(), filter.getFim(), pageable);
	}

	public List<Movimentacao> listar() {
		return movimentacaoRepository.findAll();
	}

	public void excluir(Long id) {
		movimentacaoRepository.delete(id);
	}

	public Movimentacao buscar(Long id) {
		return movimentacaoRepository.findOne(id);
	}

}
