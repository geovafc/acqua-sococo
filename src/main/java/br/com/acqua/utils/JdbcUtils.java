package br.com.acqua.utils;

public class JdbcUtils {
	public static final String USUARIO_POR_LOGIN = "SELECT username, password, enabled, nome FROM usuario"
			+ " WHERE username = ?";
	
	public static final String PERMISSOES_POR_USUARIO = "SELECT u.username, p.nome as nome_permissao FROM usuario_permissoes up"
			+ " JOIN usuario u ON u.id = up.usuario_id"
			+ " JOIN permissao p ON p.id = up.permissao_id"
			+ " WHERE u.username = ?";
}
