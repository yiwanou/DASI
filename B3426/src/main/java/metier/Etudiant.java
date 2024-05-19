/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author psaad
 */
@Entity

public class Etudiant extends Intervenant{
    private String universite;
    private String specialite;

    public Etudiant() {
    }

    public Etudiant(String universite, String specialite, String nom, String prenom, String mail, Date dateDeNaissance, int niveauMin, int niveauMax, String numeroTel) {
        super(nom, prenom, mail, dateDeNaissance, niveauMin, niveauMax, numeroTel);
        this.universite = universite;
        this.specialite = specialite;
    }

   

 

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
}
