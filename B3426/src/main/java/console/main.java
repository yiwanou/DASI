 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import dao.JpaUtil;
import dao.MatiereDao;
import enums.StatutIntervention;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.Eleve;
import metier.Etablissement;
import metier.Intervenant;
import metier.Intervention;
import metier.Matiere;
import service.Service;
import util.Message;
import util.Saisie;
import static util.Saisie.lireChaine;
import static util.Saisie.lireInteger;

/**
 *
 * @author psaad
 */
public class main {
    public static void main(String[] args) throws ParseException {
       
        JpaUtil.creerFabriquePersistance();
//        init();
       boolean exit = false;
        while (!exit) {
            System.out.println("Choisissez une option :");
            System.out.println("1. Eleve");
            System.out.println("2. Intervenant");
            System.out.println("3. Admin");
            System.out.println("4. Quitter");

            int choix = Saisie.lireInteger("Entrez votre choix : ");
            switch (choix) {
                case 1:
                    menuEleve();
                    break;
                case 2:
                     menuIntervenant();
                    break;
                case 3:
                    menuAdmin();
                   
                    break;
                case 4:
                    exit = true;
                    System.out.println("Sortie...");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir à nouveau.");
            }
        }
    }

    private static void menuEleve() throws ParseException {
        boolean back = false;
        while (!back) {
            System.out.println("1. Inscrire un Eleve");
            System.out.println("2. Log in Eleve");
            System.out.println("3. Back");
            int choix = Saisie.lireInteger("Enter your choice: ");
            switch (choix) {
                case 1:
                    inscrireEleve();
                    break;
                case 2:
                  Eleve loggedInEleve = connectionEleve();
                  if (loggedInEleve != null) {
                     menuEleveLoggedIn(loggedInEleve);
                    }
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private static void menuEleveLoggedIn(Eleve eleve) throws ParseException {
        boolean logout = false;
        while (!logout) {
            System.out.println("1. Demander Intervention");
            System.out.println("2. Afficher Historique Interventions");
            System.out.println("3.Terminer et noter Intervention");
            System.out.println("4. Log Out");
            int choix = Saisie.lireInteger("Enter your choice: ");
            switch (choix) {
                case 1:
                    demanderSoutienEleve(eleve); 
                    break;
                case 2:
                    afficherHistoriqueInterventionsEleve(eleve); 
                    break;
                case 3:
                    terminerSoutienEleve(eleve);
                    break;
                case 4:
                    logout = true;
                    System.out.println("Vous avez été déconnecté.");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir à nouveau.");
            }
        }
}

   private static void menuIntervenant() throws ParseException {
    boolean exit = false;
    while (!exit) {
        System.out.println("1. Log In");
        System.out.println("2. Back");
        int choix = Saisie.lireInteger("Enter your choice: ");
        switch (choix) {
            case 1:
                Intervenant loggedInIntervenant = connectionIntervenant();
                if (loggedInIntervenant != null) {
                    menuIntervenantLoggedIn(loggedInIntervenant);
                }
                break;
            case 2:
                exit = true;
                break;
            default:
                System.out.println("Choix invalide. Veuillez choisir à nouveau.");
        }
    }
}


    public static void inscrireEleve() throws ParseException {
    String nom = lireChaine("Votre Nom :");
    String prenom = lireChaine("Votre Prenom :");
    String mail = lireChaine("Votre adresse mail :");
    String codeEtablissement = lireChaine("Veuillez inserer le code de votre etablissement :");
    Integer niveau = lireInteger("Votre niveau (Entier entre x et y) :");
    String jour = lireChaine("Votre jour de naissance :");
    String mois = lireChaine("Votre mois de naissance :");
    String annee = lireChaine("Votre annee de naissance :");
    String date = jour + "-" + mois + "-" + annee;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Date ddn = format.parse(date); // ddn=date de naissance
    boolean succes = false;
    String password = null;
    while (!succes) {
        String mdp1 = lireChaine("Mot de Passe désiré :");
        String mdp2 = lireChaine("Confirmez votre mot de passe :");
        if (mdp1.equals(mdp2)) {
            succes = true;
            password = mdp1;
        } else {
            System.out.println("Les mots de passe ne correspondent pas, réessayez :");
        }
    }

    Eleve e = new Eleve(nom, prenom, mail, ddn, niveau);
    Service service = new Service();
    Eleve test = service.inscrireEleve(e, codeEtablissement, password);

    if (test == null) {
        System.out.println("Erreur durant l'inscription. L'établissement renseigné n'existe pas ou une autre erreur s'est produite.");
    } else {
        System.out.println("Inscription réussie !");
 
    }
}

    public static Eleve connectionEleve() {
        String mail = Saisie.lireChaine("Votre adresse mail :");
        String mdp = Saisie.lireChaine("Votre Mot de passe :");
        Service service = new Service();
        Eleve eleve = service.authentificationEleve(mail, mdp); 

        if(eleve != null) {
            System.out.println("Authentification réussie.");
        } else {
            System.out.println("Authentification échouée, veuillez réessayer.");
            eleve = null; 
        }

        return eleve; 
    }
    public static void init() throws ParseException{
        Service service=new Service();
        service.initIntervenants();
        service.initMatieres();
        Eleve e = new Eleve("tony","saad" , "f", new Date(),2 );
         service.inscrireEleve(e, "0692155T","f");
         Eleve f = new Eleve("marc","saad" , "g", new Date(), 2);
         service.inscrireEleve(f, "0692155T","g");
         Eleve g = new Eleve("paul","saad" , "h", new Date(), 4);
        service.inscrireEleve(g, "0692155T","h");
         Eleve i = new Eleve("george","saad" , "i", new Date(), 9);
        service.inscrireEleve(i, "0690132U","i");
         
        
    }
   

    public static Intervenant connectionIntervenant() {
        String mail = Saisie.lireChaine("Adresse mail:");
        String mdp = Saisie.lireChaine("Mot de passe:");
        // Assuming the Service class has a method to authenticate an Intervenant
        Service service = new Service();
        Intervenant intervenant = service.authentificationIntervenant(mail, mdp);
        if (intervenant != null) {
            System.out.println("Authentification réussie pour l'intervenant.");
            
        } else {
            System.out.println("Authentification échouée, veuillez réessayer.");
            intervenant=null;
        }
        return intervenant;
    }
    private static void menuIntervenantLoggedIn(Intervenant intervenant) throws ParseException {
    boolean logout = false;
    while (!logout) {
        System.out.println("1. Commencer Intervention");
        System.out.println("2. Afficher Historique  Interventions");
        System.out.println("3. Afficher Statistiques globale");
        System.out.println("4. Log Out");
        int choix = Saisie.lireInteger("Enter your choice: ");
        switch (choix) {
            case 1:
                 ouvrirTableauDeBordIntervention(intervenant);
                break;
            case 2:
                afficherHistoriqueInterventionsIntervenant(intervenant);
                break;
            case 3 :
                afficherStatistiquesGlobales();
            case 4:
                logout = true;
                System.out.println("Vous avez été déconnecté.");
                break;
            default:
                System.out.println("Choix invalide. Veuillez choisir à nouveau.");
        }
    }
}
    private static void menuAdmin() throws ParseException {
        boolean back = false;
        while (!back) {
            System.out.println("1. Ajouter Matiere");
            System.out.println("2. Supprimer Matiere");
            System.out.println("3. Retour");

            int choix = Saisie.lireInteger("Entrez votre choix : ");
            switch (choix) {
                case 1:
                    addMatiere(); // Vous devrez implémenter cette méthode
                    break;
                case 2:
                    supprimerMatiere();
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir à nouveau.");
            }
        }
}

    private static void addMatiere() {
        String nomMatiere = Saisie.lireChaine("Matiere a ajouter:"); 
        //Matiere matiere = new Matiere(nomMatiere); 
        Service service = new Service(); // Assuming Service class contains the ajouterMatiere method
        Matiere result = service.ajouterMatiere(nomMatiere);
        if (result != null && nomMatiere.equals(result.getNom())) {
            System.out.println("La matière '" + nomMatiere + "' a été ajoutée avec succès.");
        } else {
            System.out.println("La matière '" + nomMatiere + "' existe déjà dans la base de données.");
        }
}

    
    private static void demanderSoutienEleve(Eleve eleve) {
    MatiereDao matiereDao = new MatiereDao();
    Service service = new Service();
    
    JpaUtil.creerContextePersistance();
    List<Matiere> matieres = matiereDao.donnerListeMatieres();
    JpaUtil.fermerContextePersistance();
    
    if (matieres.isEmpty()) {
        System.out.println("Aucune matière disponible pour le soutien.");
        return;
    }
    
    System.out.println("Choisissez une matière pour le soutien :");
    for (int i = 0; i < matieres.size(); i++) {
        System.out.println((i + 1) + ". " + matieres.get(i).getNom());
    }
    int choix = Saisie.lireInteger("Entrez votre choix : ") - 1;
    
    if (choix < 0 || choix >= matieres.size()) {
        System.out.println("Choix invalide. Veuillez réessayer.");
        return;
    }
    
    String commentaire = Saisie.lireChaine("Ajoutez un commentaire pour l'intervention (facultatif): ");
    Matiere selectedMatiere = matieres.get(choix);
    Intervention intervention = service.envoyerDemande(eleve, selectedMatiere, commentaire);
    
    // Handle printing based on intervention status
    if (intervention != null) {
        if (intervention.getStatut() == StatutIntervention.EN_COURS) {
            System.out.println("Votre demande de soutien en " + selectedMatiere.getNom() + " a été enregistrée avec succès. Un intervenant est disponible, et la visio va démarrer dans quelques minutes.");
        } else if (intervention.getStatut() == StatutIntervention.ANNULEE) {
            System.out.println("Actuellement, aucun intervenant n'est disponible. Votre demande a été annulée.");
        }
    } else {
        System.out.println("Une erreur s'est produite lors de la tentative de demande de soutien.");
    }
}

    private static void ouvrirTableauDeBordIntervention(Intervenant intervenant) throws ParseException {
    boolean back = false;
    Service service =new  Service();
    Intervention intervention;
        intervention = service.obtenirInterventionEnCours(intervenant);
        if (intervention==null){
            System.out.println("intervention est null errrur dand obternir intervention en cours");
        }
    while (!back) {
        System.out.println("1. Afficher Details De l'intervention");
        System.out.println("2. Commencer Visio");
        System.out.println("3. Back");
        int choix = Saisie.lireInteger("Enter your choice: ");
        switch (choix) {
            case 1:
                afficherDetailsIntervention(intervention); 
                break;
            case 2:
                commencerIntervention(intervention); // Placeholder - implement this method
                break;
            case 3:
                back = true; // Return to the previous menu
                break;
            default:
                System.out.println("Choix invalide. Veuillez choisir à nouveau.");
        }
    }
}

   private static void afficherDetailsIntervention(Intervention intervention) {
    System.out.println("======= Details de l'Intervention =======");
    afficherMatiereIntervention(intervention); // Display la Matiere de l'intervention
    afficherProfileEleve(intervention); // Displays Le profil de l'etudiant
    System.out.println("=========================================\n");
}

    private static void afficherProfileEleve(Intervention intervention) {
    Eleve eleve = intervention.getEleve();
    Date dateNaissance = eleve.getDateDeNaissance();
    Date dateActuelle = new Date();

    long ageMillis = dateActuelle.getTime() - dateNaissance.getTime();
    long age = TimeUnit.MILLISECONDS.toDays(ageMillis) / 365;

    System.out.println("\n----- Profile de l'Eleve -----");
    System.out.println("Nom: " + eleve.getNom());
    System.out.println("Prenom: " + eleve.getPrenom());
    System.out.println("Mail: " + eleve.getMail());
    System.out.println("Date de Naissance: " + new SimpleDateFormat("dd/MM/yyyy").format(eleve.getDateDeNaissance()));
    System.out.println("Âge: " + age + " ans");
    System.out.println("Niveau: " + eleve.getNiveau());
    if (eleve.getEtablissement() != null) {
        System.out.println("Etablissement: " + eleve.getEtablissement().getNom());
    }
    System.out.println("-------------------------------\n");
}


private static void afficherMatiereIntervention(Intervention intervention) {
    Matiere matiere = intervention.getMatiere();
    System.out.println("----- Matiere de l'Intervention -----");
    System.out.println("Nom: " + matiere.getNom());
    System.out.println("Commentaire: " + (intervention.getCommentaire() != null ? intervention.getCommentaire() : "Pas de commentaire"));
    System.out.println("-------------------------------------\n");
}

    private static void commencerIntervention(Intervention intervention) {
          Service service = new Service();
    service.startVisio(intervention);
    System.out.println("\nLa visio a commencé. Simulation en cours...");

    boolean visioRunning = true;
    while (visioRunning) {
        System.out.println("\n--- Menu Visio ---");
        System.out.println("1. Terminer la visio");
        System.out.print("Entrez votre choix: ");

        int choix = Saisie.lireInteger(""); // Using Saisie.lireInteger for input

        switch (choix) {
            case 1:
               
                System.out.println("La visio est terminée.");
                terminerSoutienIntervenant(intervention);
                visioRunning = false; // Exiting the loop, thus ending the visio session simulation
                break;
            default:
                System.out.println("Option invalide. Veuillez réessayer.");
                // Loop continues, indicating the visio session is still active
                break;
        }
        } 
    }
    private static void terminerSoutienIntervenant(Intervention intervention){
        Service service = new Service();
         service.endVisio(intervention);
         String bilan = lireChaine("Veuillez ecrire le bilan de l'intervention :");
         service.envoyerBilan(bilan,intervention);
         
    }

  private static void supprimerMatiere() {
    System.out.print("Entrez le nom de la matière à supprimer : ");
    String nomMatiere = Saisie.lireChaine("");
    
    Service service = new Service();
    boolean resultat = service.supprimerMatiere(nomMatiere);
    
    if (resultat) {
        System.out.println("La suppression de la matière a réussi.");
    } else {
        System.out.println("La suppression de la matière a échoué.");
    }
}
 private static void terminerSoutienEleve(Eleve eleve) {
    Long idEleve=eleve.getIdEleve();
    int note = Saisie.lireInteger("Entrez votre note pour l'intervention (de 1 à 5): ");

    Service service = new Service();
    boolean resultat = service.noterIntervention(eleve, note); 
    if (resultat) {
        System.out.println("Votre note a été enregistrée avec succès.");
    } else {
        System.out.println("Erreur lors de l'enregistrement de votre note. Vérifiez les informations fournies.");
    }
}
    private static void afficherHistoriqueInterventionsEleve(Eleve eleve) {
    Service service = new Service();
    List<Intervention> interventions = service.obtenirInterventionsParEleve(eleve);

    if (interventions.isEmpty()) {
        System.out.println("Aucune intervention trouvée pour cet élève.");
    } else {
        System.out.println("Historique des interventions pour l'élève :");
        for (Intervention intervention : interventions) {
            String matiereNom = intervention.getMatiere() != null ? intervention.getMatiere().getNom() : "Non spécifiée";
            String intervenantNom = intervention.getIntervenant() != null ? intervention.getIntervenant().getNom() + " " + intervention.getIntervenant().getPrenom() : "Non spécifié";
            String duree = intervention.getDuree() != null ? intervention.getDuree().toString() + " secondes" : "Non spécifiée";
            String note = intervention.getNote() > 0 ? Integer.toString(intervention.getNote()) : "Non notée";
            String statut = intervention.getStatut() != null ? intervention.getStatut().toString() : "Non spécifié";
            String commentaire = intervention.getCommentaire() != null && !intervention.getCommentaire().isEmpty() ? intervention.getCommentaire() : "Aucun";
            String bilan = intervention.getBilan() != null && !intervention.getBilan().isEmpty() ? intervention.getBilan() : "Aucun";

            System.out.println("\nIntervention ID: " + intervention.getId());
            System.out.println("Date: " + new SimpleDateFormat("dd/MM/yyyy").format(intervention.getDate()));
            System.out.println("Matière: " + matiereNom);
            System.out.println("Durée: " + duree);
            System.out.println("Statut: " + statut);
            System.out.println("Note: " + note);
            System.out.println("Commentaire: " + commentaire);
            System.out.println("Bilan: " + bilan);
            System.out.println("Intervenant: " + intervenantNom);
        }
    }
}
private static void afficherHistoriqueInterventionsIntervenant(Intervenant intervenant) {
    Service service = new Service();
    List<Intervention> interventions = service.obtenirInterventionsParIntervenant(intervenant);

    if (interventions.isEmpty()) {
        System.out.println("Aucune intervention trouvée pour cet intervenant.");
    } else {
        System.out.println("Historique des interventions pour l'intervenant " + intervenant.getNom() + " " + intervenant.getPrenom() + ":");
        for (Intervention intervention : interventions) {
            String matiereNom = intervention.getMatiere() != null ? intervention.getMatiere().getNom() : "Non spécifiée";
            String eleveNom = intervention.getEleve() != null ? intervention.getEleve().getNom() + " " + intervention.getEleve().getPrenom() : "Non spécifié";
            String duree = intervention.getDuree() != null ? intervention.getDuree().toString() + " secondes" : "Non spécifiée";
            String note = intervention.getNote() > 0 ? Integer.toString(intervention.getNote()) : "Non notée";
            String statut = intervention.getStatut() != null ? intervention.getStatut().toString() : "Non spécifié";
            String commentaire = intervention.getCommentaire() != null && !intervention.getCommentaire().isEmpty() ? intervention.getCommentaire() : "Aucun";
            String bilan = intervention.getBilan() != null && !intervention.getBilan().isEmpty() ? intervention.getBilan() : "Aucun";

            System.out.println("\nIntervention ID: " + intervention.getId());
            System.out.println("Date: " + new SimpleDateFormat("dd/MM/yyyy").format(intervention.getDate()));
            System.out.println("Matière: " + matiereNom);
            System.out.println("Durée: " + duree);
            System.out.println("Statut: " + statut);
            System.out.println("Note: " + note);
            System.out.println("Commentaire: " + commentaire);
            System.out.println("Bilan: " + bilan);
            System.out.println("Élève: " + eleveNom);
        }
    }
}

    private static void afficherStatistiquesGlobales() {
         Service service = new Service();

        System.out.println("Statistiques globales :");
        System.out.println("Nombre total d'interventions : " + service.compterTotalInterventions());
        System.out.println("Durée moyenne des interventions : " + service.calculerDureeMoyenneInterventions());
        System.out.println("Nombre total d'élèves : " + service.compterTotalEleves());
        System.out.println("Nombre total d'établissements : " + service.compterTotalEtablissements());
        System.out.println("IPS moyen des établissements : " + service.calculerIpsMoyenEtablissements());
        
    }




            
}
 
 