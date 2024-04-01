package fr.eni.ecole.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfig  {
	
	
	@Bean
	UserDetailsManager userManager(DataSource dataSource) {
		//objet JDBC pour stoquer les informations récupérées depuis la BDD
		JdbcUserDetailsManager jdbcUserManager = new JdbcUserDetailsManager(dataSource);
		
		//requêtes SQL
		// 1. Requête recherchant les utilisateurs par leur identifiant, en utilisant un paramètre positionnel (?)
		jdbcUserManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo =?");
		// 2. Requête recherchant les utilisateurs par leurs rôles, en utilisant un paramètre positionnel (?)
		jdbcUserManager.setAuthoritiesByUsernameQuery("SELECT u.pseudo, r.role FROM UTILISATEURS u INNER JOIN ROLES r ON u.administrateur = r.is_admin WHERE pseudo = ?");
		return jdbcUserManager;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		// définition des roles associés aux URL correspondants
		http.authorizeHttpRequests(auth -> {
			//les trois modèles courants à définir (rappel : 'hasAnyRole' pour plusieurs et 'hasRole' pour un seul role
//			auth
//				.requestMatchers(HttpMethod.GET, " ").hasAnyRole("VENDEUR", "AQUEREUR", "ADMINISTRATEUR")
//				.requestMatchers(HttpMethod.POST, " ").hasAnyRole("VENDEUR", "AQUEREUR", "ADMINISTRATEUR")
//				.requestMatchers(" ").hasRole("ADMINISTRATEUR");
			auth.requestMatchers(HttpMethod.GET, "/user/register").permitAll()
				.requestMatchers(HttpMethod.POST, "/user/register").permitAll();
			auth.requestMatchers(HttpMethod.GET, "/user/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/user/profil").authenticated();
			auth.requestMatchers(HttpMethod.GET, "/Creer-Article").authenticated()
				.requestMatchers(HttpMethod.POST, "/Creer-Article").authenticated();
			auth.requestMatchers(HttpMethod.GET, "/articles/articleDetail/**").permitAll(); //autorisation pour la page de détail des articles
			auth.requestMatchers(HttpMethod.GET, "/articles/supprimer/**").authenticated();
			auth.requestMatchers("/*").permitAll();//l'url racine pour tout le monde
			auth.requestMatchers("/css/*").permitAll();//CSS pour tout le monde
			auth.requestMatchers("/images/*").permitAll();//images pour tout le monde
			
//			auth.anyRequest().denyAll();//accès refusé pour toutes les autres URL.
		});
		
		//on demande à spring security de charger son formulaire de connexion à défaut d'en avoir créé un nous-même
		//	http.formLogin(Customizer.withDefaults());
		
		//on créera ici notre propre page de connexion qu'on utilisera avec cette base :
		http.formLogin(form -> {
			form.loginPage("/login").permitAll();
			form.defaultSuccessUrl("/");
		});
		
		
		//on gère le logout pour vider la session et le contexte de sécurité
		http.logout(logout -> logout//lambda utilisée pour lancer la série d'instructions
			.invalidateHttpSession(true)//invalide la session
			.clearAuthentication(true)//l'authentification sera effacée après la déconnexion
			.deleteCookies("JSESSIONID")//suppression des cookies utilisateur
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//fait correspondre la procédure de déconnexion via l'objet AntPathRequestMatcher qui spécifie le shéma
			.logoutSuccessUrl("/")//redirection sur la page racine
			.permitAll()//authorisation de se déconnecter pour tous les profils et non profils
			);
		
		return http.build();
	}
	
}
