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
public class EncheresSecurityConfig {

	@Bean
	UserDetailsManager userManager(DataSource dataSource) {
		// objet JDBC pour stoquer les informations récupérées depuis la BDD
		JdbcUserDetailsManager jdbcUserManager = new JdbcUserDetailsManager(dataSource);

		// requêtes SQL
		// 1. Requête recherchant les utilisateurs par leur identifiant, en utilisant un
		// paramètre positionnel (?)
		jdbcUserManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo =?");
		// 2. Requête recherchant les utilisateurs par leurs rôles, en utilisant un
		// paramètre positionnel (?)
		jdbcUserManager.setAuthoritiesByUsernameQuery(
			"SELECT u.pseudo, r.role FROM UTILISATEURS u INNER JOIN ROLES r ON u.administrateur = r.is_admin WHERE pseudo = ?");
		return jdbcUserManager;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// définition des roles associés aux URL correspondants
		http.authorizeHttpRequests(auth -> {
			// les modèles courants à définir (rappel : 'hasAnyRole' pour plusieurs et
			// 'hasRole' pour un seul role
//			auth
//				.requestMatchers(HttpMethod.GET, " ").hasAnyRole("VENDEUR", "AQUEREUR", "ADMINISTRATEUR")
//				.requestMatchers(HttpMethod.POST, " ").hasAnyRole("VENDEUR", "AQUEREUR", "ADMINISTRATEUR")
//				.requestMatchers(" ").hasRole("ADMINISTRATEUR");
			
			auth.requestMatchers(HttpMethod.GET, "/user/**")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/user/profil")
				.authenticated()
				.requestMatchers(HttpMethod.GET, "/Creer-Article")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/Creer-Article")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/articles/articleDetail/**")
				.authenticated()
				.requestMatchers(HttpMethod.GET, "/modifier-Article/**")
				.authenticated()
				.requestMatchers(HttpMethod.GET, "/articles/supprimer/**")
				.authenticated();
			auth.requestMatchers(HttpMethod.GET, "/user/register")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/user/register")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/articles/articleDetail/**")
				.permitAll()
				.requestMatchers("/*")
				.permitAll()// l'url racine pour tout le monde
				.requestMatchers("/css/*")
				.permitAll()// CSS pour tout le monde
				.requestMatchers("/images/*")
				.permitAll()// images pour tout le monde
				.requestMatchers("/JavaScript/*")// JS pour tout le monde
				.permitAll();

//			auth.anyRequest().denyAll();//accès refusé pour toutes les autres URL.
		});

		http.formLogin(form -> {					//page de connexion 
			form.loginPage("/login")
				.permitAll();
			form.defaultSuccessUrl("/");
		});

		//logout pour vider la session et le contexte de sécurité
		http.logout(logout -> logout										// lambda utilisée pour lancer la série d'instructions
				.invalidateHttpSession(true)								// invalide la session
				.clearAuthentication(true)									// l'authentification sera effacée après la déconnexion
				.deleteCookies("JSESSIONID")								// suppression des cookies utilisateur
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))	// fait correspondre la procédure de déconnexion
				.logoutSuccessUrl("/")										// redirection sur la page racine
				.permitAll()												// authorisation de se déconnecter pour tous les profils et non profils
		);
		return http.build();
	}
}
