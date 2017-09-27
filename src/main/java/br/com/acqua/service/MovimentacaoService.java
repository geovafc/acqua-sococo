package br.com.acqua.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.mockito.internal.stubbing.answers.ThrowsException;
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

	// Ta pegando so pelo ano de 2017
	public List<MovimentacaoMesAnoDTO> getCountMovimentacoesByMesAno() {
		MovimentacaoMesAnoDTO objeto;
		List<MovimentacaoMesAnoDTO> projetosAtivos = new ArrayList<>();
		Integer ano = 2017;
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
			Long quantidadeMovimentacoes = movimentacaoRepository.countByMovimentacoesFromMesAno(i, ano);

			objeto.setMes(ano.toString() + "-" + mesPorNumero.get(i));
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

	public Page<Movimentacao> findByDataHoraBetween(MovimentacaoFilter filter, int page, int size) throws Throwable {

		// Date inicio = filter.getInicio().toString() == "" ?
		// convertStringToTimestamp(System.currentTimeMillis()) :
		// convertStringToTimestamp(filter.getInicio().getTime());
		// Date fim = filter.getFim().toString() == "" ?
		// convertStringToTimestamp(System.currentTimeMillis()) :
		// convertStringToTimestamp(filter.getFim().getTime());

		Date inicio = converterStringDateInicio(filter.getInicio());
		Date fim = converterStringDateFim(filter.getFim());

		Pageable pageable = new PageRequest(page, size);

		return movimentacaoRepository.findByDataHoraBetween(inicio, fim, pageable);
	}

	public static Date converterStringDateInicio(String date) throws Throwable {
		
		DateFormat f = DateFormat.getDateInstance();
		
		Date data = f.parse(date);
		
		String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
		System.out.println("Data timestamp " + timestamp);
		
		data = f.parse(timestamp);
		System.out.println("Data Convertida Inicio" + data);
		return data;
	}
	
	public static Date converterStringDateFim(String date) throws Throwable {
		DateFormat f = DateFormat.getDateInstance();
		
		Date data = f.parse(date);
		
		data.setDate(data.getDate() + 1);
		
		String timestamp = new SimpleDateFormat("dd/MM/yyyy").format(data);
		data = f.parse(timestamp);
		System.out.println("Data Convertida fim" + data);
		return data;
	}
	
	public static Date convertLongToDate(long currentTimeMillis) throws Throwable {
		Calendar c = Calendar.getInstance();
		Date date = new Date(currentTimeMillis - 43282800000l);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.parse(df.format(date));
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
