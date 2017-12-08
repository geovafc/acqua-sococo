package br.com.acqua.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import br.com.acqua.entity.Produto;
import br.com.acqua.entity.Usuario;
import br.com.acqua.repository.MovimentacaoRepository;
import br.com.acqua.repository.ProdutoRepository;
import br.com.acqua.repository.UserRepository;
import br.com.acqua.repository.filter.MovimentacaoFilter;

@Service
public class MovimentacaoService {

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private UserRepository userRepository;

	public void salvar(Movimentacao movimentacao, String userName) {

		Usuario user = userRepository.findByUsername(userName);

		movimentacao.setUsuario(user);
		try {
			if (movimentacao.getDataHora() == null) {
				movimentacao.setDataHora(new Date(System.currentTimeMillis()));
			}
			System.out.println("Movimentação Avatar: " + movimentacao.getAvatar().getTitulo());
			movimentacaoRepository.save(movimentacao);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	// Ta pegando so pelo ano de 2017
	public List<MovimentacaoMesAnoDTO> getCountMovimentacoesByMesAno() {
		MovimentacaoMesAnoDTO objeto;
		List<MovimentacaoMesAnoDTO> projetosAtivos = new ArrayList<>();
		//Integer ano = 2017;
		
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy");
		String ano = formatador.format( data );
		
		HashMap<Integer, String> mesPorNumero = new HashMap<>();
		mesPorNumero.put(1, "01");
		mesPorNumero.put(2, "02");
		mesPorNumero.put(3, "03");
		mesPorNumero.put(4, "04");
		mesPorNumero.put(5, "05");
		mesPorNumero.put(6, "06");
		mesPorNumero.put(7, "07");
		mesPorNumero.put(8, "08");
		mesPorNumero.put(9, "09");
		mesPorNumero.put(10, "10");
		mesPorNumero.put(11, "11");
		mesPorNumero.put(12, "12");

		for (int i = 1; i < 13; i++) {

			objeto = new MovimentacaoMesAnoDTO();
			Long quantidadeMovimentacoes = movimentacaoRepository.countByMovimentacoesFromMesAno(i, Integer.valueOf(ano.toString()));

			objeto.setMes(ano + "-" + mesPorNumero.get(i));
			objeto.setQuantidadeMovimentacoes(quantidadeMovimentacoes);
			objeto.setAno(Calendar.YEAR + "");
			projetosAtivos.add(objeto);

		}

		return projetosAtivos;

	}

	public Page<Movimentacao> findByPagination(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return movimentacaoRepository.findAllByOrderByIdAsc(pageable);
	}

	public static Date converterDateFim(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, 23);
		c.add(Calendar.MINUTE, 59);
		return c.getTime();
	}

	public List<Movimentacao> listar() {
		
		return movimentacaoRepository.findAll();
	}

	public Page<Movimentacao> pesquisar(MovimentacaoFilter filter, int page, int size) throws Throwable {

		filter = atualizarFilter(filter);

		Pageable pageable = new PageRequest(page, size);

		return movimentacaoRepository.filtrar(filter, pageable);
	}

	private MovimentacaoFilter atualizarFilter(MovimentacaoFilter filter) {
		
		Produto produto = produtoRepository.findByCodigoDeBarras(filter.getCodigo());

		filter.setProduto(produto);
		
		filter.setFim(filter.getFim() == null ? null : converterDateFim(filter.getFim()));
	
		Usuario usuario = userRepository.findByNome(filter.getNomeUsuario().toLowerCase());
		
		filter.setUsuario(usuario);
		
		return filter;
	}

	public void excluir(Long id) {
		movimentacaoRepository.delete(id);
	}

	public Movimentacao buscar(Long id) {
		return movimentacaoRepository.findOne(id);
	}

}
