package br.com.acqua.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.acqua.entity.AvatarProd;
import br.com.acqua.entity.Produto;
import br.com.acqua.repository.ProdutoRepository;
import br.com.acqua.repository.filter.ProdutoFilter;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtosRepository;

	@Autowired
	private AvatarProdService avatarService;

	public void salvar(Produto produto, MultipartFile file) {

		AvatarProd avatar = new AvatarProd();

		try {
			if (produto.getId() == null) {
				produto.setDataCadastro(Date.valueOf(LocalDate.now()));
			}
			avatar = avatarService.getAvatarByUpload(file);
			System.out.println("Tipo: " + avatar.getTipo());
			produto.setAvatar(avatar);

			produtosRepository.save(produto);

		} catch (IllegalArgumentException e) {
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
	public void update(Produto produto, MultipartFile file) {

		AvatarProd avatar = avatarService.getAvatarByUpload(file);

		System.out.println("Titulo: " + avatar.getTitulo());
		System.out.println("tipo: " + avatar.getTipo());

		try {
			if (avatar.getTitulo().equals("default.png")) {
				produtosRepository.updateNomeAndDescricaoAndCodigoBarra(produto.getNome(), produto.getDescricao(),
						produto.getCodigoDeBarras(), produto.getId());
			} else {
				AvatarProd avatarSave = avatarService.saveOrUpdate(avatar);
				produto.setAvatar(avatarSave);
				produtosRepository.save(produto);
			}

		} catch (Exception e) {
			throw new IllegalArgumentException("Falha em Atualizar o produto");
		}

	}

	public Produto findByAvatar(AvatarProd avatar) {
		return produtosRepository.findByAvatar(avatar);
	}

	public void updateEnable(Long id) {
		System.out.println("Passou aqui");
		produtosRepository.updateEnable(false, id);

	}

}
