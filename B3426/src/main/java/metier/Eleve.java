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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author psaad
 */
@Entity
public class Eleve {
    private String nom;
    private String prenom;
    @ManyToOne
    private Etablissement etablissement;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idEleve;
    @Column(nullable=false,unique=true)
    private String mail;
    private String mdp;
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;
    private Integer niveau;
    @OneToMany(mappedBy="eleve")
    private List<Intervention> interventionsEleve=new ArrayList();
    
    

    public Eleve() {
    }

    public Eleve(String nom, String prenom, String mail, Date dateDeNaissance, Integer niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.dateDeNaissance = dateDeNaissance;
        this.niveau = niveau;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Long getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(Long idEleve) {
        this.idEleve = idEleve;
    }

    public List<Intervention> getInterventionsEleve() {
        return interventionsEleve;
    }

    public void setInterventionsEleve(List<Intervention> interventionsEleve) {
        this.interventionsEleve = interventionsEleve;
    }
    public void addIntervention(Intervention intervention) {
        interventionsEleve.add(intervention);
        intervention.setEleve(this);
    }
    
}
