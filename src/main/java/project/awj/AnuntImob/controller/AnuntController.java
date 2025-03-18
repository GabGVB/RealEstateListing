package project.awj.AnuntImob.controller;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.SessionAttribute;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.awj.AnuntImob.repository.AnuntRepository;
import project.awj.AnuntImob.repository.UtilizatorRepository;
import project.awj.AnuntImob.model.Utilizator;
import project.awj.AnuntImob.model.Anunt;

@Controller
@SessionAttributes("loggedInUser")
public class AnuntController {
    private final AnuntRepository anuntRepository;
    private final UtilizatorRepository utilizatorRepository;

    public AnuntController(AnuntRepository anuntRepository, UtilizatorRepository utilizatorRepository) {
        this.anuntRepository = anuntRepository;
        this.utilizatorRepository = utilizatorRepository;
    }

    @GetMapping("/")
    public String getAllData(Model model, @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser,
                             @RequestParam(required = false) String successMessage) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        model.addAttribute("anunturi", anuntRepository.findAll());
        model.addAttribute("loggedInUser", loggedInUser);
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("showRegisterLink", true);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Utilizator user = utilizatorRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        }
        model.addAttribute("error", "Username sau parola incorecta!");
        model.addAttribute("showRegisterLink", true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();  // Ștergerea completă a sesiunii
        model.asMap().remove("loggedInUser");  // Ștergerea manuală a atributului gestionat de Spring
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        if (username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            model.addAttribute("error", "Toate câmpurile sunt obligatorii!");
            return "register";
        }
        if (utilizatorRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username-ul este deja folosit!");
            return "register";
        }
        Utilizator newUser = new Utilizator(username, email, password);
        utilizatorRepository.save(newUser);
        return "redirect:/login";
    }
    // Endpoint pentru JSON
/*
    @GetMapping("/api/anunt")
    @ResponseBody
    public List<Anunt> listaAnunturiJSON() {
        return AnuntService.getAllAnunturi();
    }
*/

    @GetMapping("/create-anunt")
    public String showCreateAnuntPage(Model model, @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        return "create-anunt";
    }

    @PostMapping("/create-anunt")
    public String createAnunt(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam int categorie,
                              @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        if (title.trim().isEmpty() || description.trim().isEmpty()){
            model.addAttribute("errorMessage", "Toate câmpurile trebuie completate!");
            return "create-anunt";
        }
        else

        if (price <= 0) {
            model.addAttribute("errorMessage", "Pretul trebuie sa fie pozitiv, mai mare a zero!");
            return "create-anunt";
        }
        Anunt newAnunt = new Anunt(title, description, price, categorie, loggedInUser);
        anuntRepository.save(newAnunt);
        redirectAttributes.addFlashAttribute("successMessage", "Anunțul a fost creat cu succes!");
        return "redirect:/anunturile_mele";
    }

    @GetMapping("/filter-anunturi")
    public String filterAnunturiByCategorie(@RequestParam int categorie,
                                            @RequestParam(required = false) Double minPrice,
                                            @RequestParam(required = false) Double maxPrice,
                                            Model model,
                                            @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser) {
       if (loggedInUser == null) {
            return "redirect:/login";
        }

        StringBuilder filtre = new StringBuilder("Filtrat după: ");
        if (categorie != -1) filtre.append("Categorie: ").append(categorie).append(", ");
        if (minPrice != null) filtre.append("Preț minim: ").append(minPrice).append(", ");
        if (maxPrice != null) filtre.append("Preț maxim: ").append(maxPrice).append(", ");
        model.addAttribute("filtrareInfo", filtre.toString());

        List<Anunt> anunturi;

        if (categorie != -1) {
            if (minPrice != null && maxPrice != null) {
                anunturi = anuntRepository.findByCategorieAndPriceBetween(categorie, minPrice, maxPrice);
            } else if (minPrice != null) {
                anunturi = anuntRepository.findByCategorieAndPriceGreaterThanEqual(categorie, minPrice);
            } else if (maxPrice != null) {
                anunturi = anuntRepository.findByCategorieAndPriceLessThanEqual(categorie, maxPrice);
            } else {
                anunturi = anuntRepository.findByCategorie(categorie);
            }
        } else {
            // Dacă nu e specificată o categorie, filtrăm doar după preț
            if (minPrice != null && maxPrice != null) {
                anunturi = anuntRepository.findByPriceBetween(minPrice, maxPrice);
            } else if (minPrice != null) {
                anunturi = anuntRepository.findByPriceGreaterThanEqual(minPrice);
            } else if (maxPrice != null) {
                anunturi = anuntRepository.findByPriceLessThanEqual(maxPrice);
            } else {
                anunturi = anuntRepository.findAll();
            }
        }

        model.addAttribute("anunturi", anunturi);
        return "index";
    }


    @GetMapping("/anunturile_mele")
    public String getAnunturileMele(Model model, @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("anunturi", anuntRepository.findByVanzator(loggedInUser));
        return "anunturile_mele";
    }

    @PostMapping("/delete-anunt")
    public String deleteAnunt(@RequestParam Long anuntId,
                              @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser,
                              Model model) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("confirmDelete", anuntId);
        return "confirm-delete";
    }

    @PostMapping("/confirm-delete")
    public String confirmDeleteAnunt(@RequestParam Long anuntId,
                                     @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser,
                                     RedirectAttributes redirectAttributes) {

        if (loggedInUser == null) {
            return "redirect:/login";
        }
        anuntRepository.deleteById(anuntId);
        redirectAttributes.addFlashAttribute("successMessage", "Anunțul a fost șters cu succes!");
        return "redirect:/anunturile_mele";
    }

    @GetMapping("/edit-anunt")
    public String editAnuntForm(@RequestParam Long anuntId, Model model, @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Anunt anunt = anuntRepository.findById(anuntId).orElse(null);
        if (anunt == null || !anunt.getVanzator().getId().equals(loggedInUser.getId())) {
            return "redirect:/anunturile-mele";
        }
        model.addAttribute("anunt", anunt);
        model.addAttribute("anuntId", anuntId);
        return "edit-anunt";
    }

    @PostMapping("/edit-anunt")
    public String editAnunt(@RequestParam Long anuntId,
                            @RequestParam String title,
                            @RequestParam String description,
                            @RequestParam double price,
                            @RequestParam int categorie,
                            @SessionAttribute(name="loggedInUser", required=false) Utilizator loggedInUser,
                            Model model,
                            RedirectAttributes redirectAttributes)  {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        if (title.trim().isEmpty() || description.trim().isEmpty()){
            model.addAttribute("errorMessage", "Toate câmpurile trebuie completate!");
            return "edit-anunt";
        }
        else

        if (price <= 0) {
            model.addAttribute("errorMessage", "Pretul trebuie sa fie pozitiv, mai mare a zero!");
            return "edit-anunt";
        }

        Anunt anunt = anuntRepository.findById(anuntId).orElse(null);
        if (anunt != null && anunt.getVanzator().getId().equals(loggedInUser.getId())) {
            anunt.setTitle(title);
            anunt.setDescription(description);
            anunt.setPrice(price);
            anunt.setCategorie(categorie);
            anuntRepository.save(anunt);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Anunțul a fost modificat cu succes!");
        return "redirect:/anunturile_mele";
    }
}
