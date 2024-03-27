// Attend que le DOM soit chargé
document.addEventListener("DOMContentLoaded", function() {
    // Récupérer le bouton "Submit"
    const submitButton = document.getElementById('submitButton');

    // Ajouter un gestionnaire d'événement de clic sur le bouton "Submit"
    submitButton.addEventListener('click', function(event) {
        event.preventDefault(); // Empêcher la soumission du formulaire par défaut
        registerUser(); // Appeler la fonction registerUser
    });
});

function registerUser() {
    // Récupérer les données du formulaire
    const pseudo = document.getElementById('pseudo').value;
    const prenom = document.getElementById('prenom').value;
    const nom = document.getElementById('nom').value;
    const email = document.getElementById('email').value;
    const telephone = document.getElementById('telephone').value;
    const rue = document.getElementById('rue').value;
    const codePostal = document.getElementById('codePostal').value;
    const ville = document.getElementById('ville').value;
    const password = document.getElementById('motDePasse').value;
    const confirmation = document.getElementById('confirmation').value;

    // Un magnifique Regex de vérification de Password parce qu'on adore les Regex!!!
    const passwordRegex = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,20}$/;
    const isPasswordValid = passwordRegex.test(password);

    if (!isPasswordValid) {
        document.getElementById('passwordError').textContent = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial et doit avoir une longueur comprise entre 8 et 20 caractères.";
        return;
    }

    // Vérifier le format de l'adresse e-mail
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        document.getElementById('emailError').textContent = "L'adresse e-mail n'est pas valide.";
        return;
    }

    // Vérifier que la confirmation et le mot de passe correspondent
    if (password !== confirmation) {
        document.getElementById('confirmPasswordError').textContent = "Les mots de passe ne correspondent pas.";
        return;
    }

    
}