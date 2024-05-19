// Get all buttons with class="nav-button"
var buttons = document.getElementsByClassName("nav-button");

// Loop through the buttons and add the active class to the current/clicked button
for (var i = 0; i < buttons.length; i++) {
  buttons[i].addEventListener("click", function () {
    var current = document.getElementsByClassName("active");
    if (current.length > 0) {
      current[0].className = current[0].className.replace(" active", "");
    }
    this.className += " active";
  });
}

$(document).ready(function () {
  $("#bouton-notification").on("click", function () {
    // Fonction appelée lors du clic sur le bouton

    console.log("clic sur le bouton de notifications"); // LOG dans Console Javascript
    $("#notification").html("A la recherche d'interventions en cours..."); // Message pour le paragraphe de notification

    // Appel AJAX
    $.ajax({
      url: "./ActionServlet",
      method: "POST",
      data: {
        todo: "accueilEleve",
      },
      dataType: "json",
    })
      .done(function (response) {
        // Fonction appelée en cas d'appel AJAX réussi
        console.log("Response", response); // LOG dans Console Javascript
        if (response.intervention) {
          $("#notification").html(
            "Tu as un soutien en cours. Rends toi sur la page visio !"
          ); // Message pour le paragraphe de notification
        } else {
          $("#notification").html("Pas d'intervenant trouvé"); // Message pour le paragraphe de notification
        }
      })
      .fail(function (error) {
        // Fonction appelée en cas d'erreur lors de l'appel AJAX
        console.log("Error", error); // LOG dans Console Javascript
        alert("Erreur lors de l'appel AJAX");
      })
      .always(function () {
        // Fonction toujours appelée
      });
  });
});
