package br.com.acqua.repository.movimentacao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.acqua.entity.Movimentacao;
import br.com.acqua.entity.Movimentacao_;
import br.com.acqua.repository.filter.MovimentacaoFilter;

public class MovimentacaoRepositoryImpl implements MovimentacaoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Movimentacao> filtrar(MovimentacaoFilter movimentacaoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> criteria = builder.createQuery(Movimentacao.class);
		Root<Movimentacao> root = criteria.from(Movimentacao.class);

		// Criar restrições
		Predicate[] predicates = criarRestricoes(movimentacaoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Movimentacao> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(movimentacaoFilter));
	}

	private Long total(MovimentacaoFilter movimentacaoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Movimentacao> root = criteria.from(Movimentacao.class);
		
		Predicate[] predicates = criarRestricoes(movimentacaoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Movimentacao> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

	}

	private Predicate[] criarRestricoes(MovimentacaoFilter movimentacaoFilter, CriteriaBuilder builder,
			Root<Movimentacao> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(movimentacaoFilter.getCodigo())) {
			predicates
					.add(builder.like(builder.lower(root.get("produto")), "%" + movimentacaoFilter.getProduto() + "%"));
		}
		if (!StringUtils.isEmpty(movimentacaoFilter.getNotaFiscal())) {
			predicates.add(builder.like(builder.lower(root.get(Movimentacao_.notaFiscal)),
					"%" + movimentacaoFilter.getNotaFiscal() + "%"));
		}
		if (movimentacaoFilter.getInicio() != null) {
			System.out.println("Data inicio: " + movimentacaoFilter.getInicio());
			builder.greaterThanOrEqualTo(root.get(Movimentacao_.dataHora), movimentacaoFilter.getInicio());
		}
		if (movimentacaoFilter.getFim() != null) {
			builder.lessThanOrEqualTo(root.get(Movimentacao_.dataHora), movimentacaoFilter.getFim());
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
