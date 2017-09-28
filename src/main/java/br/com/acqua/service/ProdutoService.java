package br.com.acqua.service;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;
import br.com.acqua.repository.ProdutoRepository;
import br.com.acqua.repository.filter.ProdutoFilter;

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
			throw new IllegalArgumentException("Código de barra já existente");
		}
	}

	public Page<Produto> findByPagination(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return produtosRepository.findAllByOrderByIdAsc(pageable);
	}

	public List<Produto> findAll() {
		return produtosRepository.findAll();
	}

	public void delete(Long id) {
		produtosRepository.delete(id);
	}

	public Produto findById(Long id) {
		return produtosRepository.findOne(id);
	}

	public Produto findByCodigo(ProdutoFilter filtro) {
		String codigo = filtro.getCodigo() == null ? "%" : filtro.getCodigo();
		return produtosRepository.findByCodigoDeBarras(codigo);
	}

	@Transactional(readOnly = false)
	public void updateNomeAndDescricaoAndCodigoBarra(Produto produto) {
		System.out.println(produto.getNome() + produto.getDescricao() + produto.getCodigoDeBarras() + produto.getId());
		produtosRepository.updateNomeAndDescricaoAndCodigoBarra(produto.getNome(), produto.getDescricao(),
				produto.getCodigoDeBarras(), produto.getId());
	}

	public Produto findByAvatar(AvatarProd avatar) {
		return produtosRepository.findByAvatar(avatar);
	}

}
