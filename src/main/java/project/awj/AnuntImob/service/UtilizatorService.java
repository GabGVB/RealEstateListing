package project.awj.AnuntImob.service;

import org.springframework.stereotype.Service;
import project.awj.AnuntImob.repository.UtilizatorRepository;
import project.awj.AnuntImob.model.Utilizator;

import java.util.List;

@Service
public class UtilizatorService {
    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public List<Utilizator> getAllUtilizatori() {
        return utilizatorRepository.findAll();
    }
}
