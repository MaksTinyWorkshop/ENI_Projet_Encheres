package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.ArticleAVendre;
import fr.eni.ecole.encheres.dal.ArticleDAOImpl;

@Configuration
public class SynchroService {
///////////////////////////////////////////// Attributs
	
	private ArticleDAOImpl articleDAO;//dépendance

///////////////////////////////////////////// Constructeurs

	public SynchroService(ArticleDAOImpl articleDAO) {
	this.articleDAO = articleDAO;
	}

////////////////////////////////////////////Méthodes
	@Transactional
    public void updateStatus(LocalDate lastCheck) {
    	LocalDate nowTime = LocalDate.now();
    	if (!lastCheck.equals(nowTime)) {
        	System.out.println("Lancement de la procédure de mise à jour des status");
        	List<ArticleAVendre> articles = articleDAO.getAllArticles();					//constitu une liste de tous les articles
        	for (ArticleAVendre a : articles) {												//envoie une requête de vérification pour chaque article
        		articleDAO.updateStatus(a);
        	}
        	System.out.println("L'actualisation des statu est effective !");
        	lastCheck = nowTime;
        }
        else {
        	System.out.println("Tous les status sont déjà à jour");
        }
    }
}
