package br.com.acqua.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.acqua.dto.MovimentacaoMesAnoDTO;
import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Usuario;
import br.com.acqua.repository.MovimentacaoRepository;
import br.com.acqua.repository.UserRepository;

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
	
	public List<MovimentacaoMesAnoDTO> getListaDeProjetosAtivosEPropostasMesAno() {
		MovimentacaoMesAnoDTO objeto;
		List<MovimentacaoMesAnoDTO> projetosAtivos = new ArrayList<>();

		HashMap<Integer, String> mesPorNumero = new HashMap<>();
		mesPorNumero.put(1, "JANEIRO");
		mesPorNumero.put(2, "FEVEREIRO");
		mesPorNumero.put(3, "MARÇO");
		mesPorNumero.put(4, "ABRIL");
		mesPorNumero.put(5, "MAIO");
		mesPorNumero.put(6, "JUNHO");
		mesPorNumero.put(7, "JULHO");
		mesPorNumero.put(8, "AGOSTO");
		mesPorNumero.put(9, "SETEMBRO");
		mesPorNumero.put(10, "OUTUBRO");
		mesPorNumero.put(11, "NOVEMBRO");
		mesPorNumero.put(12, "DEZEMBRO");

		for (int i = 1; i < 13; i++) {

			objeto = new MovimentacaoMesAnoDTO();
			Long quantidadeMovimentacoes = movimentacaoRepository.countByMovimentacoesFromMesAno(i);

			objeto.setMes(mesPorNumero.get(i));
			objeto.setQuantidadeMovimentacoes(quantidadeMovimentacoes);
			objeto.setAno(Calendar.YEAR+"");
			projetosAtivos.add(objeto);

		}

		return projetosAtivos;

	}
	
	public Page<Movimentacao> findByPagination(int page, int size){
		Pageable pageable = new PageRequest(page, size);
		return movimentacaoRepository.findAllByOrderByIdAsc(pageable);
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
