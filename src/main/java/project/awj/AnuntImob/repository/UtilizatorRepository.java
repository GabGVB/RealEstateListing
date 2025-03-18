package project.awj.AnuntImob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.awj.AnuntImob.model.Utilizator;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
    Utilizator findByUsername(String username); // Metodă necesară pentru autentificare
}
