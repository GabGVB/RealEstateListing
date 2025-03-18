package project.awj.AnuntImob.service;

import org.springframework.stereotype.Service;
import project.awj.AnuntImob.repository.AnuntRepository;
import project.awj.AnuntImob.model.Anunt;

import java.util.List;

@Service
public class AnuntService {
    private final AnuntRepository anuntRepository;

    public AnuntService(AnuntRepository anuntRepository) {
        this.anuntRepository = anuntRepository;
    }

    public List<Anunt> getAllAnunturi() {
        return anuntRepository.findAll();
    }
}
