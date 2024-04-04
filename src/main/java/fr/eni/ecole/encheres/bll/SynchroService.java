package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SynchroService {
	
	private static LocalDate lastCheck = LocalDate.now();

    //@Bean
    private void updateStatus(LocalDate lastCheck) {
        if (lastCheck != LocalDate.now()) {
        	lastCheck = LocalDate.now();
        	System.out.println("Lancement de la procédure de mise à jour des status");
        	//lancement de la vérification des status
        }
        else {
        	System.out.println("Tous les status sont à jour");
        }
    }

	public static LocalDate getLastCheck() {
		return lastCheck;
	}

	public static void setLastCheck(LocalDate lastCheck) {
		SynchroService.lastCheck = lastCheck;
	}
}
