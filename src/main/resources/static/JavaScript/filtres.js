document.addEventListener("DOMContentLoaded", function() {
    // Récupération du formulaire de filtrage par nom d'article
    let formNomArticle = document.querySelector('form[action="/filtrer-par-nom-article"]');

    // Ajout de l eventListener de type : "onclick" pour le formulaire de filtrage par nom d'article
    formNomArticle.addEventListener("submit", function(event) {
        event.preventDefault(); 

        // Appel de la fonction filtrerParNomArticle
        filtrerParNomArticle();
    });

    // Récupération du formulaire de filtrage par catégorie
    let formCategorie = document.querySelector('form[action="/filtrer-par-categorie"]');

    // Ajout de l'EventListener de type : "onclick" pour le formulaire de filtrage par catégorie
    formCategorie.addEventListener("submit", function(event) {
        event.preventDefault(); 

        // Appel de la fonction filtrerParCategorie
        filtrerParCategorie();
    });
});