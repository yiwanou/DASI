/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author psaad
 */
@Entity
public class Etablissement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idEtablissement;
    @Column(nullable=false,unique=true)
    private String code;
    private String nom;
    private String secteur;
    private String  codeCommune;
    private String nomCommune;
    private String  codeDepartement;
    private String nomDepartement;
    private String academie;
    private Double ips;
    private Double latitude;
    private Double longitude;
    @OneToMany(mappedBy="etablissement")
    private List<Eleve> elevesEtablissement=new ArrayList();
    
    public Etablissement() {
    }

    public Etablissement(String code, String nom, String secteur, String codeCommune, String nomCommune, String codeDepartement, String nomDepartement, String academie, Double ips) {
        this.code = code;
        this.nom = nom;
        this.secteur = secteur;
        this.codeCommune = codeCommune;
        this.nomCommune = nomCommune;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
        this.academie = academie;
        this.ips = ips;
    }

    public Etablissement (String code, String nom, String secteur, String codeCommune, String nomCommune, String codeDepartement, String nomDepartement, String academie, Double ips, Double latitude, Double longitude) {
        this.code = code;
        this.nom = nom;
        this.secteur = secteur;
        this.codeCommune = codeCommune;
        this.nomCommune = nomCommune;
        this.codeDepartement = codeDepartement;
        this.nomDepartement = nomDepartement;
        this.academie = academie;
        this.ips = ips;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    public String getAcademie() {
        return academie;
    }

    public void setAcademie(String academie) {
        this.academie = academie;
    }

    public Double getIps() {
        return ips;
    }

    public void setIps(Double ips) {
        this.ips = ips;
    }

    public Long getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(Long idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Eleve> getElevesEtablissement() {
        return elevesEtablissement;
    }

    public void setElevesEtablissement(List<Eleve> elevesEtablissement) {
        this.elevesEtablissement = elevesEtablissement;
    }
    
    
    
}
