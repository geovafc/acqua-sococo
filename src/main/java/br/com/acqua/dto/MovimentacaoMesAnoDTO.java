package br.com.acqua.dto;

import java.io.Serializable;

public class MovimentacaoMesAnoDTO implements Serializable{
	
	private Long quantidadeMovimentacoes;
		
	private String mes;
	
	private String ano;

	public Long getQuantidadeMovimentacoes() {
		return quantidadeMovimentacoes;
	}

	public void setQuantidadeMovimentacoes(Long quantidadeMovimentacoes) {
		this.quantidadeMovimentacoes = quantidadeMovimentacoes;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
	
	


	
	

	
	
	
	
}
