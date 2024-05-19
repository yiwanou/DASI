/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.maps.model.LatLng;
import dao.EleveDao;
import dao.EtablissementDao;
import dao.IntervenantDao;
import dao.InterventionDao;
import dao.JpaUtil;
import dao.MatiereDao;
import enums.StatutIntervention;
import enums.TypeEtablissement;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import metier.Autre;
import metier.Eleve;
import metier.Enseignant;
import metier.Etablissement;
import metier.Etudiant;
import metier.Intervenant;
import metier.Intervention;
import metier.Matiere;
import util.EducNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author psaad
 */
public class Service {

    public Eleve inscrireEleve(Eleve eleve, String codeEtablissement, String password) {
        EleveDao eleveDao = new EleveDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            Etablissement etab = inscrireEtablissement(codeEtablissement);
            eleve.setEtablissement(etab);
            eleve.setMdp(password);
            eleveDao.create(eleve);
            if (etab == null) {
                throw new NullPointerException("L'etablissement renseigné n'existe pas");
            }
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            eleve = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
//        if (eleve == null) {
//
//            Message.envoyerMail("contact@instructif", eleve.getMail(), "Echec de l'inscription sur le reseaux INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + ", Ton inscription sur le reseau INSTRUCT'IF a malencontreusement echoue... Merci de reessayer ulterieurement");
//        } else {
//
//            Message.envoyerMail("contact@instructif", eleve.getMail(), "Bienvenue  sur le reseaux INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + ", nous te confirmons ton inscription sur le reseau INSTRUCT'IF . Si tu as besoin d'un soutien pour tes lecons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant");
//        }
        return eleve;
    }

    public Eleve authentificationEleve(String mail, String mdp) {
        EleveDao eleveDao = new EleveDao();
        JpaUtil.creerContextePersistance();
        Eleve eleve = eleveDao.findByMail(mail);

        if (eleve != null ) {
            JpaUtil.fermerContextePersistance();
        } else {
            JpaUtil.fermerContextePersistance();
            eleve = null;
        }
        return eleve;
    }

    public Intervenant authentificationIntervenant(String mail, String mdp) {
        IntervenantDao intervenantDao = new IntervenantDao();
        JpaUtil.creerContextePersistance();
        Intervenant intervenant = intervenantDao.findByMail(mail);
        if (intervenant != null && (mdp == null ? intervenant.getMdp() == null : mdp.equals(intervenant.getMdp()))) {
            JpaUtil.fermerContextePersistance();
        } else {
            JpaUtil.fermerContextePersistance();
            intervenant = null;
        }
        return intervenant;
    }

    private Etablissement inscrireEtablissement(String code) throws IOException {
        EtablissementDao etablissementDao = new EtablissementDao();
        Etablissement etab = etablissementDao.findByCode(code);

        if (etab == null) { // Si l'établissement n'est pas trouvé dans la base de données
            EducNetApi api = new EducNetApi();
            List<String> result = api.getInformationCollege(code); // D'abord essayer de le trouver comme un collège
            if (result == null) { // Si non trouvé comme collège, essayer comme lycée
                result = api.getInformationLycee(code);
            }

            if (result != null) { // Si l'établissement est trouvé
                String uai = result.get(0);
                String nom = result.get(1);
                String secteur = result.get(2);
                String codeCommune = result.get(3);
                String nomCommune = result.get(4);
                String codeDepartement = result.get(5);
                String nomDepartement = result.get(6);
                String academie = result.get(7);
                String ips = result.size() > 8 ? result.get(8) : null; // Vérifier si IPS existe
                String adresseEtablissement = result.get(3) + ", " + result.get(4); // Supposition: l'adresse est combinée de codeCommune + nomCommune

                LatLng coordsEtablissement = GeoNetApi.getLatLng(adresseEtablissement);
                Double latitude = null;
                Double longitude = null;
                if (coordsEtablissement != null) {
                    latitude = coordsEtablissement.lat;
                    longitude = coordsEtablissement.lng;
                }

                etab = new Etablissement(uai, nom, secteur, codeCommune, nomCommune, codeDepartement, nomDepartement, academie, ips != null ? Double.parseDouble(ips) : null, latitude, longitude);
                etablissementDao.create(etab); // Persister le nouvel établissement

                System.out.println("Etablissement " + uai + ": " + nom + " à " + nomCommune + ", " + nomDepartement);
            } else {
                System.out.println("Etablissement inconnu pour le code : " + code);
                etab = null;
            }
        }

        return etab;
    }

    public Matiere ajouterMatiere(String nomMatiere) {
        MatiereDao matiereDao = new MatiereDao();
        JpaUtil.creerContextePersistance();
        Matiere matiere = matiereDao.trouverMatiereParNom(nomMatiere); // Directly use Dao method
        try {
            if (matiere == null) {
                // Matiere does not exist, so let's create it
                matiere = new Matiere(nomMatiere); // Assuming Matiere has a constructor with name
                JpaUtil.ouvrirTransaction();
                matiereDao.create(matiere);
                JpaUtil.validerTransaction();

            } else {
                matiere = null;

            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            matiere = null;
            throw new RuntimeException("Transaction failed", ex);
        } finally {
            JpaUtil.fermerContextePersistance(); // Close EntityManager at the end of the process
        }
        return matiere;
    }

    public boolean supprimerMatiere(String nomMatiere) {
        JpaUtil.creerContextePersistance();
        boolean reussi;
        try {
            MatiereDao matiereDao = new MatiereDao();
            Matiere matiere = matiereDao.trouverMatiereParNom(nomMatiere);
            if (matiere != null) {
                JpaUtil.ouvrirTransaction();
                matiereDao.supprimer(matiere);
                JpaUtil.validerTransaction();
                reussi = true;
            } else {
                reussi = false;
            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            throw new RuntimeException("La transaction de suppression a échoué", ex);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return reussi;
    }

    public void initIntervenants() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date dateAutre = format.parse("15-06-1995");
        Date dateEnseignant = format.parse("25-09-1990");
        Date dateEtudiant = format.parse("01-01-2000");

        // Création d'intervenants avec niveauMin et niveauMax
        Autre autre = new Autre("Consultant", "Doe", "John", "john.doe@example.com",
                dateAutre, 1, 3, "123456789");
        Enseignant enseignant = new Enseignant(TypeEtablissement.UNIVERSITE,
                "Smith", "Alice", "alice.smith@example.com",
                dateEnseignant, 4, 5, "987654321");
        Etudiant etudiant = new Etudiant("University of XYZ", "Computer Science",
                "Johnson", "Bob", "bob.johnson@example.com",
                dateEtudiant, 1, 3, "456123789");

        etudiant.setMdp("1");
        enseignant.setMdp("1");
        autre.setMdp("1");

        IntervenantDao intervenantDao = new IntervenantDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            intervenantDao.create(autre);
            intervenantDao.create(enseignant);
            intervenantDao.create(etudiant);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace(); // Pour le débogage
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void initMatieres() {
        Matiere matiere1 = new Matiere("Mathematics");
        Matiere matiere2 = new Matiere("Physics");
        Matiere matiere3 = new Matiere("Chemistry");  // Persist each matiere
        MatiereDao matiereDao = new MatiereDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            matiereDao.create(matiere1);
            matiereDao.create(matiere2);
            matiereDao.create(matiere3);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }

    public Long startVisio(Intervention intervention) {
        Long startTime = System.currentTimeMillis();
        intervention.setStatut(StatutIntervention.EN_COURS);
        intervention.setDuree(startTime);

        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            InterventionDao interventionDao = new InterventionDao();
            interventionDao.update(intervention);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return startTime;
    }

    public void endVisio(Intervention intervention) {
        Long endTime = System.currentTimeMillis();
        Long totalTimeMillis = endTime - intervention.getDuree();
        Long totalSeconds = totalTimeMillis / 1000;
        intervention.setDuree(totalSeconds);

        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            InterventionDao interventionDao = new InterventionDao();
            interventionDao.update(intervention);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void envoyerBilan(String bilan, Intervention intervention) {
        intervention.setBilan(bilan);
        intervention.setStatut(StatutIntervention.TERMINEE);
        intervention.getIntervenant().setDisponible(true);

        Message.envoyerMail("contact@instruct.if", intervention.getEleve().getMail(),
                "Bilan de votre intervention avec " + intervention.getIntervenant().getNom(), bilan);

        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            InterventionDao interventionDao = new InterventionDao();
            IntervenantDao intervenantDao = new IntervenantDao();
            interventionDao.update(intervention);
            intervenantDao.update(intervention.getIntervenant());
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    private Intervenant trouverIntervenantDisponible(int niveauEleve) {
        IntervenantDao intervenantDao = new IntervenantDao();
        List<Intervenant> intervenantsDisponibles = intervenantDao.trouverTousIntervenantsDisponibles();
        Intervenant intervenantSelectionne = null;
        int minimumInterventions = Integer.MAX_VALUE;

        for (Intervenant intervenant : intervenantsDisponibles) {
            if (niveauEleve >= intervenant.getNiveauMin() && niveauEleve <= intervenant.getNiveauMax()) {
                int taille = intervenant.getInterventionsIntervenant().size();
                if (taille < minimumInterventions) {
                    minimumInterventions = taille;
                    intervenantSelectionne = intervenant;
                }
            }
        }

        return intervenantSelectionne;
    }

    public Intervention envoyerDemande(Eleve eleve, Matiere matiere, String commentaire) {
        InterventionDao interventionDao = new InterventionDao();
        IntervenantDao intervenantDao = new IntervenantDao();
        Intervention intervention = new Intervention();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            intervention.setEleve(eleve);
            intervention.setMatiere(matiere);
            intervention.setCommentaire(commentaire);
            intervention.setDate(new Date());
            intervention.setStatut(StatutIntervention.EN_ATTENTE);

            Intervenant intervenant = trouverIntervenantDisponible(eleve.getNiveau());
            if (intervenant != null) {
                intervention.setIntervenant(intervenant);
                intervenant.addIntervention(intervention);
                eleve.addIntervention(intervention);
                intervenant.setDisponible(false);
                intervention.setStatut(StatutIntervention.EN_COURS);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String heure = sdf.format(intervention.getDate());
                Message.envoyerNotification(intervenant.getNumeroTel(), "Bonjour " + intervenant.getNom() + ". Merci de prendre en charge la demande de soutien en <<" + intervention.getMatiere().getNom() + ">> demande a " + heure + " par " + intervention.getEleve().getPrenom() + " en niveau " + intervention.getEleve().getNiveau());
            } else {
                intervention.setStatut(StatutIntervention.ANNULEE);
            }

            interventionDao.create(intervention);
            if (intervenant != null) {
                intervenantDao.update(intervenant);
            }

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            intervention = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return intervention;
    }

    public Intervention obtenirInterventionEnCours(Intervenant intervenant) {

        if (intervenant == null || intervenant.getIdIntervenant() == null) {
            throw new IllegalArgumentException("Intervenant is null or has a null ID.");
        }
        InterventionDao interventionDao = new InterventionDao();
        List<Intervention> interventions = null;
        try {
            JpaUtil.creerContextePersistance();
            interventions = interventionDao.donnerListeInterventionEnCoursPourIntervenant(intervenant);

        } catch (Exception ex) {
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return interventions.stream().findFirst().orElse(null);
    }

    public boolean noterIntervention(Eleve eleve, int note) {
        boolean reussi;
        List<Intervention> interventions = null;
        InterventionDao interventionDao = new InterventionDao();

        try {
            JpaUtil.creerContextePersistance();
            interventions = interventionDao.donnerListeInterventionNonNoterPourEleve(eleve);
            Intervention interventionANoter = interventions.stream().findFirst().orElse(null);

            if (interventionANoter != null) {
                JpaUtil.ouvrirTransaction();
                interventionANoter.setNote(note);
                interventionDao.update(interventionANoter);
                JpaUtil.validerTransaction();
                reussi = true;
            } else {
                reussi = false;
            }
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            reussi = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return reussi;
    }

    public List<Intervention> obtenirInterventionsParEleve(Eleve eleve) {
        JpaUtil.creerContextePersistance();
        InterventionDao interventionDao = new InterventionDao();
        List<Intervention> toutesLesInterventions = interventionDao.donnerListeInterventions();
        JpaUtil.fermerContextePersistance();

        return toutesLesInterventions.stream()
                .filter(intervention -> intervention.getEleve().getIdEleve().equals(eleve.getIdEleve()))
                .collect(Collectors.toList());
    }

    public List<Intervention> obtenirInterventionsParIntervenant(Intervenant intervenant) {
        if (intervenant == null || intervenant.getIdIntervenant() == null) {
            throw new IllegalArgumentException("L'intervenant est null ou n'a pas d'ID valide.");
        }
        InterventionDao interventionDao = new InterventionDao();
        List<Intervention> interventions = null;

        try {
            JpaUtil.creerContextePersistance();
            interventions = interventionDao.donnerListeInterventionsParIntervenant(intervenant);

        } catch (Exception ex) {

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return interventions;
    }

    public long compterTotalInterventions() {
        long total = 0;
        JpaUtil.creerContextePersistance();
        try {
            InterventionDao interventionDao = new InterventionDao();
            total = interventionDao.compterTotalInterventions();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return total;
    }

    public double calculerDureeMoyenneInterventions() {
        double moyenne = 0;
        JpaUtil.creerContextePersistance();
        try {
            InterventionDao interventionDao = new InterventionDao();
            moyenne = interventionDao.calculerDureeMoyenneInterventions();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return moyenne;
    }

    public long compterTotalEleves() {
        long total = 0;
        JpaUtil.creerContextePersistance();
        try {
            EleveDao eleveDao = new EleveDao();
            total = eleveDao.compterTotalEleves();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return total;
    }

    public long compterTotalEtablissements() {
        long total = 0;
        JpaUtil.creerContextePersistance();
        try {
            EtablissementDao etablissementDao = new EtablissementDao();
            total = etablissementDao.compterTotalEtablissements();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return total;
    }

    public double calculerIpsMoyenEtablissements() {
        double moyenne = 0;
        JpaUtil.creerContextePersistance();
        try {
            EtablissementDao etablissementDao = new EtablissementDao();
            moyenne = etablissementDao.calculerIpsMoyenEtablissements();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return moyenne;
    }
    
    public List<Matiere> obtenirListeMatieres() {
        
        MatiereDao matiereDao = new MatiereDao();
        List<Matiere> matieres = null;

        try {
            JpaUtil.creerContextePersistance();
            matieres = matiereDao.donnerListeMatieres();

        } catch (Exception ex) {

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return matieres;
    }
    
}
