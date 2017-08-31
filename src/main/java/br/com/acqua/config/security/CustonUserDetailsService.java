package br.com.acqua.config.security;

import static br.com.acqua.utils.JdbcUtils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustonUserDetailsService implements UserDetailsService{
	
	private static final Logger logger = Logger.getLogger(CustonUserDetailsService.class.getSimpleName());

	@Autowired
	private DataSource dataSource;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();

			CustonUserDetails userDetails = buscarUsuario(connection, username);

			Collection<GrantedAuthority> permissoesPorUsuario = buscarPermissoes(connection,
					username, PERMISSOES_POR_USUARIO);
			
			for (GrantedAuthority grantedAuthority : permissoesPorUsuario) {
				System.out.println(grantedAuthority.getAuthority());
			}

			userDetails.getAuthorities().addAll(permissoesPorUsuario);

			return userDetails;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problemas com a tentativa de conexão!", e);
			throw new UsernameNotFoundException("Problemas com a tentativa de conexão!", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Problemas ao tentar fechar a conexão!", e);
				throw new UsernameNotFoundException("Problemas ao tentar fechar a conexão!", e);
			}
		}
	}
	
	public CustonUserDetails buscarUsuario(Connection connection, String username) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(USUARIO_POR_LOGIN);
		ps.setString(1, username);

		ResultSet rs = ps.executeQuery();

		if (!rs.next()) {
			throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
		}

		String nome = rs.getString("nome");
		String password = rs.getString("password");
		boolean enabled = rs.getBoolean("enabled");

		rs.close();
		ps.close();

		return new CustonUserDetails(nome, username, password, enabled);
	}
	
	public Collection<GrantedAuthority> buscarPermissoes(Connection connection, String username, String sql) throws SQLException {
		List<GrantedAuthority> permissoes = new ArrayList<>();

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, username);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			permissoes.add(new SimpleGrantedAuthority(rs.getString("nome_permissao")));
		}

		rs.close();
		ps.close();

		return permissoes;
	}

}
