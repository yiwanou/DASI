/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import enums.StatutIntervention;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author psaad
 */


 @Entity
public class Intervention {
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToOne
    private Matiere matiere;
    private Long duree;
    private StatutIntervention statut;
    private String commentaire;
    private String bilan;
    private int note;
    @ManyToOne
    private Intervenant intervenant;
    @ManyToOne
    private Eleve eleve;

    // Constructeur, getters, setters

    public Intervention() {
    }

    public Intervention(Date date, Matiere matiere, Long duree, StatutIntervention statut, String commentaire, int note) {
        this.date = date;
        this.matiere = matiere;
        this.duree = duree;
        this.statut = statut;
        this.commentaire = commentaire;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public StatutIntervention getStatut() {
        return statut;
    }

    public void setStatut(StatutIntervention statut) {
        this.statut = statut;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getBilan() {
        return bilan;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
  
}

