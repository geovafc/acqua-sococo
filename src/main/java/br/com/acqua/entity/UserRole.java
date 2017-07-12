package br.com.acqua.entity;

/**
 * 
 * @author Jairo Sousa
 * 06/01/2017
 */

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
