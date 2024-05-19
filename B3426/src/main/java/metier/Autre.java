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
public class Autre extends Intervenant{
    private String activite;

    public Autre() {
    }

    public Autre(String activite) {
        this.activite = activite;
    }

    public Autre(String activite, String nom, String prenom, String mail, Date dateDeNaissance, int niveauMin, int niveauMax, String numeroTel) {
        super(nom, prenom, mail, dateDeNaissance, niveauMin, niveauMax, numeroTel);
        this.activite = activite;
    }

   

    

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
    
    
}
