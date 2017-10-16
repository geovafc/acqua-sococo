package br.com.acqua.repository.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.acqua.entity.Produto;
import br.com.acqua.entity.Usuario;

public class MovimentacaoFilter {

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date inicio;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fim;
	private Produto produto;
	private String codigo;
	private String notaFiscal;
	private String nomeUsuario;
	private Usuario usuario;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
