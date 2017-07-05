package br.com.acqua.entity;

public enum UserRole {
	USER("Usuário"), ADMIN("Administrador");

	private String descricao;

	private UserRole(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
