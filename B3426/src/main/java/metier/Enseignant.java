/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import enums.TypeEtablissement;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Entity;

/**
 *
 * @author psaad
 */
@Entity
public class Enseignant extends Intervenant {
    private TypeEtablissement typeEtablissement;

    public Enseignant() {
    }

    public Enseignant(TypeEtablissement typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public Enseignant(TypeEtablissement typeEtablissement, String nom, String prenom, String mail, Date dateDeNaissance, int niveauMin, int niveauMax, String numeroTel) {
        super(nom, prenom, mail, dateDeNaissance, niveauMin, niveauMax, numeroTel);
        this.typeEtablissement = typeEtablissement;
    }


 

  

    public TypeEtablissement getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(TypeEtablissement typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }
    

   
    
    
}


