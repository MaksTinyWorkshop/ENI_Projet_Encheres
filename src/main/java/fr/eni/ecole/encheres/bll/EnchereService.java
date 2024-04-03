package fr.eni.ecole.encheres.bll;

public interface EnchereService {
	void placerUneEnchere(String pseudoUser, long idArticle, int montantEnchere);
}
