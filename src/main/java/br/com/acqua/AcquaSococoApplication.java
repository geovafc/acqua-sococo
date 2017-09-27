package br.com.acqua;

import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
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
		
		Calendar c = Calendar.getInstance();
		c.set(2017, 9, 26, 0, 0, 0);
		Date data = c.getTime();
		DateFormat f = DateFormat.getDateInstance();
		Locale brasil = new Locale("pt", "BR"); 
		
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.FULL, brasil);
		System.out.println("Data e hora brasileira: "+f2.format(data));
	}
}
