package project.awj.AnuntImob.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.awj.AnuntImob.model.Anunt;
import project.awj.AnuntImob.model.Utilizator;

public interface AnuntRepository extends JpaRepository<Anunt, Long> {
    List<Anunt> findByCategorie(int categorie);
    List<Anunt> findByVanzator(Utilizator vanzator);
    List<Anunt> findByCategorieAndPriceBetween(int categorie,double minPrice,double maxPrice);
    List<Anunt> findByPriceBetween(double minPrice,double maxPrice);
    List<Anunt> findByCategorieAndPriceGreaterThanEqual(int categorie,double minPrice);
    List<Anunt> findByCategorieAndPriceLessThanEqual(int categorie,double maxPrice);
    List<Anunt> findByPriceGreaterThanEqual(double minPrice);
    List<Anunt> findByPriceLessThanEqual(double maxPrice);;
}