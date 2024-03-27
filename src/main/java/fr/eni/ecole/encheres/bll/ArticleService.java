package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.exceptions.BusinessException;

public interface ArticleService {
	List<ArticleAVendre> charger();
}
