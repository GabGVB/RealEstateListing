package project.awj.AnuntImob;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import project.awj.AnuntImob.model.Anunt;
import project.awj.AnuntImob.repository.AnuntRepository;

@SpringBootApplication
public class AnuntImobApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnuntImobApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(AnuntRepository repository) {
		return args -> {
			repository.save(new Anunt("Vand Apartament", "Apartament 3 camere", 50000.0, "Bucuresti", "0741122334"));
		};
	}
}
