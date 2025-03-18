/** Clasa pentru ........
 * @author Nume Student
 * @version 10 Decembrie 2024
 */
package project.awj.AnuntImob.model;

import jakarta.persistence.*;

@Entity
public class Anunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Cheie primară auto-generată
    private String title;
    private String description;
    private Double price;
    private int categorie; // 0 - Teren, 1 - Apartament, 2 - Casă

    @ManyToOne
    @JoinColumn(name = "vanzator_id", nullable = false)
    private Utilizator vanzator;

    public Anunt() {}

    public Anunt(String title, String description, Double price, int categorie, Utilizator vanzator) {
    //    if (title.isEmpty() || description.isEmpty() || price <= 0)
        this.title = title;
        this.description = description;
        this.price = price;
        this.categorie = categorie;
        this.vanzator = vanzator;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public int getCategorie() {
        return categorie;
    }

    public Utilizator getVanzator() {
        return vanzator;
    }

    public void setVanzator(Utilizator vanzator) {
        this.vanzator = vanzator;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public void setCategorie (int categorie){
        this.categorie=categorie;
    }
    public void setPrice(Double price){
        this.price=price;
    }
    @Override
    public String toString() {
        return "Anunt{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categorie=" + categorie +
                ", vanzator=" + vanzator.getUsername() +
                '}';
    }
}
