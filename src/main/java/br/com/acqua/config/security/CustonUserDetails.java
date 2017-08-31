package br.com.acqua.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustonUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String username;
	private String password;
	private boolean enabled;
	private Collection<GrantedAuthority> permissoes = new ArrayList<>();

	public CustonUserDetails(String nome, String username, 
			String password, boolean enabled) {
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return permissoes;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
