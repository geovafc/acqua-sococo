package br.com.acqua.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author Jairo Sousa 06/01/2017
 */

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O NOME é obrigatorio")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotEmpty(message = "O CÓDIGO DE BARRAS é obrigatorio")
	@Column(name = "codigo_de_barras", nullable = false, unique = true)
	private String codigoDeBarras;

	@NotEmpty(message = "A DESCRIÇÃO é obrigatoria")
	@Column(name = "descricao")
	private String descricao;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, optional = true)
	@JoinColumn(name = "avatar_id")
	public AvatarProd avatar;

	@Column(name = "imagem_content_type")
	private String imagemContentType;
	
	@Column(name = "enabled")
	private boolean enabled;

	@OneToMany(mappedBy = "produto", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Movimentacao> movimentacoes = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public AvatarProd getAvatar() {
		return avatar;
	}

	public void setAvatar(AvatarProd avatar) {
		this.avatar = avatar;
	}

	public String getImagemContentType() {
		return imagemContentType;
	}

	public void setImagemContentType(String imagemContentType) {
		this.imagemContentType = imagemContentType;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Produto produto = (Produto) o;
		if (produto.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), produto.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Produto{" + "id=" + getId() + ", codigoDeBarras='" + getCodigoDeBarras() + "'" + ", descricao='"
				+ getDescricao() + "'" + ", imagem='" + getAvatar().getTitulo() + "'" + ", imagemContentType='"
				+ imagemContentType + "'" + "}";
	}
}
