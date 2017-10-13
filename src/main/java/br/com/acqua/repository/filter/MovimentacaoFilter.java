package br.com.acqua.repository.filter;

public class MovimentacaoFilter {

	private String inicio;
	private String fim;
	private String codigo;
	private String notaFilcal;

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNotaFilcal() {
		return notaFilcal;
	}

	public void setNotaFilcal(String notaFilcal) {
		this.notaFilcal = notaFilcal;
	}

}
