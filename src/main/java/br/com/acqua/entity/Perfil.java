package br.com.acqua.entity;

public enum Perfil {
	USER("Usuário"), ADMIN("Administrador");

	private String descricao;

	private Perfil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
