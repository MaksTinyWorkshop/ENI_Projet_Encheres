document.addEventListener('DOMContentLoaded', function() {
    const achatsRadio = document.getElementById('achats');
    const mesVentesRadio = document.getElementById('mesVentes');
    const enchereFiltreForm = document.getElementById('enchereFiltreForm');
    const venteFiltreForm = document.getElementById('venteFiltreForm');

    function updateFormState() {
        if (achatsRadio.checked) {
            rendreFormVentesInactif();
            rendreFormEncheresActif();
        } else if (mesVentesRadio.checked) {
            rendreFormEncheresInactif();
            rendreFormVentesActif();
        }
    }

    achatsRadio.addEventListener('click', updateFormState);
    mesVentesRadio.addEventListener('click', updateFormState);

    // Initialiser l'état du formulaire au chargement de la page
    updateFormState();

    // Fonction pour rendre le formulaire de ventes actif
    function rendreFormVentesActif() {
        const checkboxesVentes = venteFiltreForm.querySelectorAll('input[type="checkbox"]');
        checkboxesVentes.forEach(function(checkbox) {
            checkbox.disabled = false;
        });
    }

    // Fonction pour rendre le formulaire d'enchères actif
    function rendreFormEncheresActif() {
        const checkboxesEncheres = enchereFiltreForm.querySelectorAll('input[type="checkbox"]');
        checkboxesEncheres.forEach(function(checkbox) {
            checkbox.disabled = false;
        });
    }

    // Fonction pour rendre le formulaire de ventes inactif
    function rendreFormVentesInactif() {
        const checkboxesVentes = venteFiltreForm.querySelectorAll('input[type="checkbox"]');
        checkboxesVentes.forEach(function(checkbox) {
            checkbox.disabled = true;
        });
    }

    // Fonction pour rendre le formulaire d'enchères inactif
    function rendreFormEncheresInactif() {
        const checkboxesEncheres = enchereFiltreForm.querySelectorAll('input[type="checkbox"]');
        checkboxesEncheres.forEach(function(checkbox) {
            checkbox.disabled = true;
        });
    }
});