  document.addEventListener('DOMContentLoaded', function() {
        // Récupération des éléments de formulaire et d'options radio
        const typeFiltreForm = document.getElementById('typeFiltreForm');
        const achatsRadio = document.getElementById('achats');
        const mesVentesRadio = document.getElementById('mesVentes');

        const enchereFiltreForm = document.getElementById('enchereFiltreForm');
        const venteFiltreForm = document.getElementById('venteFiltreForm');

        // Écouteur d'événement pour les changements de sélection de type de filtre
        typeFiltreForm.addEventListener('change', function() {
            // Si ACHATS est sélectionné, afficher le formulaire d'enchères et masquer le formulaire de ventes
            if (achatsRadio.checked) {
                enchereFiltreForm.style.display = 'block';
                venteFiltreForm.style.display = 'none';
            } 
            // Si MES VENTES est sélectionné, afficher le formulaire de ventes et masquer le formulaire d'enchères
            else if (mesVentesRadio.checked) {
                venteFiltreForm.style.display = 'block';
                enchereFiltreForm.style.display = 'none';
            }
        });

        // Déclencher l'événement de changement au chargement de la page pour afficher le bon formulaire initialement
        typeFiltreForm.dispatchEvent(new Event('change'));
    });