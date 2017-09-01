package br.com.acqua.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.acqua.config.security.CustonUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustonUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
			.antMatchers("/imagens/").permitAll()
			.antMatchers("/usuarios/").hasAnyRole("ROLE_ADMIN")
			.antMatchers("/produto/").hasAnyRole("ROLE_USER")
			.antMatchers("/movimentacoes/").hasAnyRole("ROLE_USER")
			.anyRequest()
			.authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll()
			.and()
				.rememberMe()
				.userDetailsService(userDetailsService);

	}
}




/*<<<<<<< HEAD
package br.com.acqua.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.acqua.config.security.CustonUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustonUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
			.antMatchers("/usuarios/").hasAnyRole("ROLE_ADMIN")
			.antMatchers("/produto/").hasAnyRole("ROLE_USER")
			.antMatchers("/movimentacoes/").hasAnyRole("ROLE_USER")
			.anyRequest()
			.authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll()
			.and()
				.rememberMe()
				.userDetailsService(userDetailsService);

	}
}
=======*/
