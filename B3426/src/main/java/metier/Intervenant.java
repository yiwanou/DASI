/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author psaad
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Intervenant {
    private String nom;
    private String prenom;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIntervenant;
    @Column(nullable = false, unique = true)
    private String mail;
    private String mdp;
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;
    private int niveauMin;
    private int niveauMax;
    private String numeroTel; // numero de telephone
    private boolean disponible;
    @OneToMany(mappedBy = "intervenant")
    private List<Intervention> interventionsIntervenant = new ArrayList<>();
    
    public Intervenant() {
        this.disponible = true;
    }

    public Intervenant(String nom, String prenom, String mail, Date dateDeNaissance, int niveauMin, int niveauMax, String numeroTel) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.dateDeNaissance = dateDeNaissance;
        this.niveauMin = niveauMin;
        this.niveauMax = niveauMax;
        this.numeroTel = numeroTel;
        this.disponible = true;
    }

    // Getters and Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }
    public Date getDateDeNaissance() { return dateDeNaissance; }
    public void setDateDeNaissance(Date dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
    public int getNiveauMin() { return niveauMin; }
    public void setNiveauMin(int niveauMin) { this.niveauMin = niveauMin; }
    public int getNiveauMax() { return niveauMax; }
    public void setNiveauMax(int niveauMax) { this.niveauMax = niveauMax; }
    public String getNumeroTel() { return numeroTel; }
    public void setNumeroTel(String numeroTel) { this.numeroTel = numeroTel; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public List<Intervention> getInterventionsIntervenant() { return interventionsIntervenant; }
    public void setInterventionsIntervenant(List<Intervention> interventionsIntervenant) { this.interventionsIntervenant = interventionsIntervenant; }
    public Long getIdIntervenant() { return idIntervenant; }
    public void setIdIntervenant(Long idIntervenant) { this.idIntervenant = idIntervenant; }
     public void addIntervention(Intervention intervention) {
        interventionsIntervenant.add(intervention);
        intervention.setIntervenant(this);
    }
}
