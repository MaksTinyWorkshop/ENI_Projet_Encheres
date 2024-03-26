package fr.eni.ecole.encheres.bll.contexte;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface ContexteService {
	Utilisateur charger(String pseudo);
	

}
