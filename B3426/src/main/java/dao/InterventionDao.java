/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import enums.StatutIntervention;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.Eleve;
import metier.Intervenant;
import metier.Intervention;

/**
 *
 * @author psaad
 */
public class InterventionDao {
       public void create(Intervention intervention) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        em.persist(intervention);
}
       public Intervention update(Intervention intervention){
           return JpaUtil.obtenirContextePersistance().merge(intervention);
       }
    public Intervention findById(Long idIntervention) {
        return JpaUtil.obtenirContextePersistance().find(Intervention.class, idIntervention);// returns null if said etablissement is not in database
    }

     public  List<Intervention> donnerListeInterventions(){
        List<Intervention> interventions = null;
        
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            Query query = em.createQuery("select a from Intervention a ", Intervention.class);
            interventions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        
        return interventions;
    }
     public  List<Intervention> donnerListeInterventionsParIntervenant(Intervenant intervenant){
         List<Intervention> interventions = null;
        
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            TypedQuery<Intervention> query = em.createQuery(
                    "SELECT i FROM Intervention i WHERE i.intervenant = :intervenant", 
                    Intervention.class);
            query.setParameter("intervenant", intervenant);
            interventions = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        
        return interventions;
    }
     
      public  List<Intervention> donnerListeInterventionEnCoursPourIntervenant(Intervenant intervenant){
        List<Intervention> interventions=null; 
        
        try {
            EntityManager em=JpaUtil.obtenirContextePersistance();
             interventions = em.createQuery(
        "SELECT i FROM Intervention i WHERE i.intervenant = :intervenant AND i.duree is NULL", Intervention.class)
        .setParameter("intervenant", intervenant)
        .setMaxResults(1)
        .getResultList();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        
        return interventions;
    }
       public  List<Intervention> donnerListeInterventionNonNoterPourEleve(Eleve eleve){
        
        List<Intervention> interventions=null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            interventions= em.createQuery(
                "SELECT i FROM Intervention i WHERE i.eleve = :eleve AND i.note =0 AND i.statut = :statutTermine", Intervention.class)
                .setParameter("eleve", eleve)
                .setParameter("statutTermine", StatutIntervention.TERMINEE)
                .getResultList();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        
        return interventions;
    }
   public long compterTotalInterventions() {
    EntityManager em = JpaUtil.obtenirContextePersistance();
    return em.createQuery("SELECT COUNT(i) FROM Intervention i", Long.class)
             .getSingleResult();
}
   public double calculerDureeMoyenneInterventions() {
    EntityManager em = JpaUtil.obtenirContextePersistance();
    Double resultat = em.createQuery("SELECT AVG(i.duree) FROM Intervention i", Double.class)
                        .getSingleResult();
    return resultat != null ? resultat : 0;
}

    
     
     
     
     
     }


