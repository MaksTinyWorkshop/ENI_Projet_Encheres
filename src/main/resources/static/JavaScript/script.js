// Attend que le DOM soit chargé
document.addEventListener("DOMContentLoaded", function() {
    // Récupérer le bouton "Submit"
    var submitButton = document.getElementById('submitButton');

    // Ajouter un gestionnaire d'événement de clic sur le bouton "Submit"
    submitButton.addEventListener('click', function(event) {
        event.preventDefault(); // Empêcher la soumission du formulaire par défaut
        registerUser(); // Appeler la fonction registerUser
    });
});

function registerUser() {
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

    // Un magnifique Regex de vérification de Password parce qu'on adore les Regex!!!
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

    // Vérifier que la confirmation et le mot de passe correspondent
    if (password !== confirmation) {
        document.getElementById('confirmPasswordError').textContent = "Les mots de passe ne correspondent pas.";
        return;
    }

    // Si toutes les validations sont réussies, vous pouvez afficher un message de succès ou effectuer d'autres actions nécessaires
    alert("Formulaire validé avec succès !");

    
}