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

	private final UtilisateurService utilisateurService;

	public UserController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	/////// Mapping du formulaire User
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		Utilisateur user = new Utilisateur();
		
		model.addAttribute("user", user);
		
		return "view-register-form";
	}

	/////// Méthode pour enregistrer l'utilisateur et son adresse
	@PostMapping("/register") 
	public String registerUser(@Valid @ModelAttribute("user") Utilisateur user, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "view-register-form";
	    } else {
	        try {
	            // Enregistre l'utilisateur
	            utilisateurService.save(user);
	            
	            // Enregistre l'adresse utilisateur
	            if (user.getAdresse() != null) {
	                utilisateurService.saveAddress(user.getPseudo(), user.getAdresse());
	            }

	            return "redirect:/"; // Redirige vers al page d'accueil
	        
	        } catch (BusinessException e) {
	            // Affiche message d'erreur
	            e.getClefsExternalisations().forEach(key -> {
	                ObjectError error = new ObjectError("globalError", key);
	                bindingResult.addError(error);
	            });
	            return "view-register-form";
	        }
	    }
	}

	///////// METHODE D'AFFICHAGE ET UPDATE DU PROFIL PERSO
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
		return "redirect:/";
	}

	@PostMapping("/profil")
	public String mettreAJourMonProfil(@Valid @ModelAttribute("user") Utilisateur user, BindingResult bindingResult, 
			@RequestParam(name = "motDePasseNew") Optional<String> nouveauMdp,
			@RequestParam(name = "confirmation") Optional<String> confirmNouveauMdp) {
		
		if (bindingResult.hasErrors()) {
			return "view-mon-profil";
		} else {
				
			try {
				// Méthode update qui prend en paramètre les données du formulaire et le user en base pour son pseudo et idAdresse
				utilisateurService.update(user);
				
				// Gestion en cas de changement de mot de passe
				if (bindingResult.hasErrors()) {
					System.out.println("echec MDP");
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
				// Afficher les messages d’erreur - il faut les injecter dans le contexte de
				// BindingResult
				e	.getClefsExternalisations()
					.forEach(key -> {
						ObjectError error = new ObjectError("globalError", key);
						bindingResult.addError(error);
					});

				return "view-mon-profil";
			}
		}
	}
	
	/////// METHODE D'AFFICHAGE DU PROFIL D'UN AUTRE UTILISATEUR
	@GetMapping("/profil/{pseudo}")
	public String afficherUnAutreProfil(
			@PathVariable(name="pseudo", required = false)String pseudo, Model model, Principal ppal) {
		Utilisateur userEnBase = utilisateurService.consulterProfil(pseudo);
		if (userEnBase.getPseudo().equals(ppal.getName())) {
			return "redirect:/user/profil";
		} else {
		model.addAttribute("user", userEnBase);
		return "view-profil-utilisateur";
		}
	}
}
