package project.awj.AnuntImob.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "utilizator")
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;  // Parola adăugată pentru autentificare

    @OneToMany(mappedBy = "vanzator")
    private List<Anunt> anunturi;

    public Utilizator() {}

    public Utilizator(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
