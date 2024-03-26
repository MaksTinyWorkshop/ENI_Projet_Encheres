package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleAVendre;

public interface ArticleService {
	List<ArticleAVendre> charger();
}
