// Attend que le DOM soit chargé
document.addEventListener("DOMContentLoaded", function() {
});

function registerUser(event) {
            event.preventDefault(); // Empêcher la soumission du formulaire par défaut
            
          // Vérifier si le bouton "Annuler" a été cliqué
   			 if (event.submitter && event.submitter.id === 'cancelButton') {
          // Rediriger vers une autre page ou effectuer une autre action en cas d'annulation
     	     window.location.href = '/accueil';
     	     return;
 		     }
            
          // Récupérer les données du formulaire
   			   var pseudo = document.getElementById('pseudo').value;
  			   var prenom = document.getElementById('prenom').value;
 			   var nom = document.getElementById('nom').value;
 			   var email = document.getElementById('email').value;
 			   var telephone = document.getElementById('telephone').value;
  			   var rue = document.getElementById('rue').value;
  			   var codePostal = document.getElementById('codePostal').value;
  			   var ville = document.getElementById('ville').value;
  			   var password = document.getElementById('motDePasse').value;
  			   var confirmation = document.getElementById('confirmation').value;

         // Un magnifique Regex de vérification de Passowrd parce qu'on adore les Regex!!!
            var passwordRegex = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,20}$/;
            var isPasswordValid = passwordRegex.test(password);

            if (!isPasswordValid) {
                document.getElementById('passwordError').textContent = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial et doit avoir une longueur comprise entre 8 et 20 caractères.";
                return;
            }
            
        // Vérifier le format de l'adresse e-mail
   		   var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
           if (!emailRegex.test(email)) {
           document.getElementById('emailError').textContent = "L'adresse e-mail n'est pas valide.";
           return;
    }

	   	// vérifie que la confirmation et le mot de passe correspondent
            if (password !== confirmPassword) {
                document.getElementById('confirmPasswordError').textContent = "Les mots de passe ne correspondent pas.";
                return;
            }
			fetch('/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username: username, email: email, password: password }),
            })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/index'; // Redirige vers la page d'Accueil après inscription réussie
                } else {
                    throw new Error('Error during registration');
                }
            })
            .catch(error => console.error('Registration failed:', error));
}