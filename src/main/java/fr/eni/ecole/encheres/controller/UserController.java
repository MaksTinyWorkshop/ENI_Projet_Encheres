package fr.eni.ecole.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UtilisateurService utilisateurService;

	public UserController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("formObject", new Utilisateur());
		return "view-register-form";
	}

	public String registerUser(@Valid @ModelAttribute("user") Utilisateur user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "view-register-form";
		}

		// Transmettre l'objet utilisateur à la couche de service pour enregistrement
		utilisateurService.enregistrerUtilisateur(user);

		return "redirect:/"; // Redirigez l'utilisateur vers la page d'accueil
	}

	///////// METHODE D'AFFICHAGE ET UPDATE DU PROFIL
	@GetMapping("/profil")
	public String afficherMonProfil(Model model, Principal ppal) {
		String pseudo = ppal.getName();
		Utilisateur user = utilisateurService.consulterProfil(pseudo);
		if (user != null) {
			model.addAttribute("user", user);
			return "view-mon-profil";
		} else {
			System.out.println("User inconnu");
		}
		return "redirect:/profil";
	}

	// TODO faire cette méthode pour mettre à jour le profil du user connecté
	@PostMapping("/profil")
	public String mettreAJourMonProfil(@ModelAttribute("user") Utilisateur user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "view-mon-profil";
		} else {
			try {
				System.out.println("User récpuéré depuis le formulaire :");
				System.out.println(user);
				
				utilisateurService.update(user);
				
				return "redirect:/";
				
			} catch (BusinessException e) {
				// Afficher les messages d’erreur - il faut les injecter dans le contexte de
				// BindingResult
				e.getClefsExternalisations().forEach(key -> {
					ObjectError error = new ObjectError("globalError", key);
					bindingResult.addError(error);
				});
				
				return "view-mon-profil";
			}
		}
	}
}
