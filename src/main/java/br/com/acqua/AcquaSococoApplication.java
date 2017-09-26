package br.com.acqua;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import br.com.acqua.repository.MovimentacaoRepository;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories()
public class AcquaSococoApplication implements CommandLineRunner {

	@Autowired
	private MovimentacaoRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AcquaSococoApplication.class, args);
	}

	@Bean
	public FixedLocaleResolver localeResolver() {

		return new FixedLocaleResolver(new Locale("pt", "BR"));

	}

	@Override
	public void run(String... args) throws Exception {
		
		Date inicio = Date.valueOf(LocalDate.of(2017, 8, 19));
		
		Date fim = Date.valueOf(LocalDate.of(2017, 8, 19));
		
		System.out.println(inicio);
		
		repository.findByDataHoraBetween(inicio, fim).forEach(System.out::println);

	}
}
