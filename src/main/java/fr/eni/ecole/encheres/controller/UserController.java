package fr.eni.ecole.encheres.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

///////////////////////////////////////////// Attributs

	private final UtilisateurService utilisateurService;

///////////////////////////////////////////// Constructeurs

	public UserController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

////////////////////////////////////////////Méthodes

	@GetMapping("/register")
	public String showRegisterForm(Model model) { // Mapping du formulaire User
		Utilisateur user = new Utilisateur();

		model.addAttribute("user", user);

		return "view-register-form";
	}

	@PostMapping("/register") // Méthode pour enregistrer l'utilisateur et son adresse
	public String registerUser(@Valid @ModelAttribute("user") Utilisateur user, BindingResult bindingResult,
			@RequestParam(name = "confirmation", required = true) String confirm) {
		if (bindingResult.hasErrors()) {
			return "view-register-form";
		} else {
			try {
				if (user.getMotDePasse()
						.equals(confirm)) {
					try {
						utilisateurService.save(user);
						return "redirect:/"; // Redirige vers al page d'accueil

					} catch (BusinessException e) {
						// Affiche message d'erreur
						e	.getClefsExternalisations()
							.forEach(key -> {
								ObjectError error = new ObjectError("globalError", key);
								bindingResult.addError(error);
							});
					}
				}
				return "view-register-form";
			} catch (BusinessException e) {
				// Affiche message d'erreur
				e	.getClefsExternalisations()
					.forEach(key -> {
						ObjectError error = new ObjectError("globalError", key);
						bindingResult.addError(error);
					});

				return "view-register-form";
			}
		}
	}

	@GetMapping("/profil")
	public String afficherMonProfil(Model model, Principal ppal) { // affichage du profil
		String pseudo = ppal.getName();
		Utilisateur user = utilisateurService.consulterProfil(pseudo);
		if (user != null) {
			model.addAttribute("user", user);
			return "view-mon-profil";
		} else {
			System.out.println("User inconnu");
		}
		return "redirect:/";
	}

	@PostMapping("/profil") // Mise à jour du profil
	public String mettreAJourMonProfil(@Valid @ModelAttribute("user") Utilisateur user, BindingResult bindingResult,
			@RequestParam(name = "motDePasseNew") Optional<String> nouveauMdp,
			@RequestParam(name = "confirmation") Optional<String> confirmNouveauMdp) {

		if (bindingResult.hasErrors()) {
			return "view-mon-profil";
		} else {
			try {
				utilisateurService.update(user);
				// Gestion en cas de changement de mot de passe
				if (bindingResult.hasErrors()) {
					return "view-mon-profil";
				} else if (nouveauMdp.isPresent() && confirmNouveauMdp.isPresent() && nouveauMdp.get()
																								.equals(confirmNouveauMdp.get())) {
					try {
						utilisateurService.updatePassword(user.getPseudo(), nouveauMdp.get());
					} catch (BusinessException e) {
						e	.getClefsExternalisations()
							.forEach(key -> {
								ObjectError error = new ObjectError("globalError", key);
								bindingResult.addError(error);
							});
					}
				}
				return "redirect:/";
			} catch (BusinessException e) {
				// Afficher les messages d’erreur
				e	.getClefsExternalisations()
					.forEach(key -> {
						ObjectError error = new ObjectError("globalError", key);
						bindingResult.addError(error);
					});
				return "view-mon-profil";
			}
		}
	}

	@GetMapping("/profil/{pseudo}") // affichage du profil d'un autre utilisateur
	public String afficherUnAutreProfil(@PathVariable(name = "pseudo", required = false) String pseudo, Model model,
			Principal ppal) {
		Utilisateur userEnBase = utilisateurService.consulterProfil(pseudo);
		if (userEnBase	.getPseudo()
						.equals(ppal.getName())) {
			return "redirect:/user/profil";
		} else {
			model.addAttribute("user", userEnBase);
			return "view-profil-utilisateur";
		}
	}
}
