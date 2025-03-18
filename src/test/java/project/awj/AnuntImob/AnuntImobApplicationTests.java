package project.awj.AnuntImob.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.awj.AnuntImob.model.Anunt;
import project.awj.AnuntImob.model.Utilizator;
import project.awj.AnuntImob.repository.AnuntRepository;
import project.awj.AnuntImob.repository.UtilizatorRepository;

public class AnuntImobApplicationTests {

	@Mock
	private AnuntRepository anuntRepository;

	@Mock
	private UtilizatorRepository utilizatorRepository;

	@Mock
	private Model model;

	@Mock
	private RedirectAttributes redirectAttributes;

	@InjectMocks
	private AnuntController anuntController;

	public AnuntImobApplicationTests() {
		MockitoAnnotations.openMocks(this);
		anuntController = new AnuntController(anuntRepository, utilizatorRepository);
	}

	@Test
	public void testCreateAnunt_Success() {
		// Arrange
		Utilizator testUser = new Utilizator("testUser", "test@example.com", "password123");
		String title = "Casa Noua";
		String description = "Casa frumoasa";
		double price = 120000.0;
		int categorie = 1;

		// Act
		String result = anuntController.createAnunt(title, description, price, categorie, testUser, model, redirectAttributes);

		// Assert
		verify(anuntRepository, times(1)).save(any(Anunt.class));
		verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "Anunțul a fost creat cu succes!");
		assertEquals("redirect:/anunturile_mele", result);
	}

	@Test
	public void testCreateAnunt_EmptyTitle() {
		// Arrange
		Utilizator testUser = new Utilizator("testUser", "test@example.com", "password123");

		// Act
		String result = anuntController.createAnunt("", "Casa frumoasa", 120000.0, 1, testUser, model, redirectAttributes);

		// Assert
		verify(model, times(1)).addAttribute(eq("errorMessage"), anyString());
		verify(anuntRepository, never()).save(any(Anunt.class));
		assertEquals("create-anunt", result);
	}

	@Test
	public void testCreateAnunt_InvalidPrice() {
		// Arrange
		Utilizator testUser = new Utilizator("testUser", "test@example.com", "password123");

		// Act
		String result = anuntController.createAnunt("Casa Noua", "Casa frumoasa", -1000.0, 1, testUser, model, redirectAttributes);

		// Assert
		verify(model, times(1)).addAttribute(eq("errorMessage"), anyString());
		verify(anuntRepository, never()).save(any(Anunt.class));
		assertEquals("create-anunt", result);
	}
}
