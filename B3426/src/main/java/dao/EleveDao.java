/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.Eleve;

/**
 *
 * @author psaad
 */
public class EleveDao {
    public void create (Eleve eleve){
       JpaUtil.obtenirContextePersistance().persist(eleve);
    }
    public Eleve update(Eleve eleve){
           return JpaUtil.obtenirContextePersistance().merge(eleve);
       }
    public Eleve findByMail(String mail) {
     
        Eleve resultat = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            Query query = em.createQuery("select a from Eleve a where a.mail = :unMail", Eleve.class);
            query.setParameter("unMail", mail);
            resultat = (Eleve) query.getSingleResult();
            
        } catch (Exception e) {
                
        }
        return resultat;
    }
    public Eleve findById(Long idEleve) {
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, idEleve);// returns null if said etablissement is not in database
    }
    public static List<Eleve> donnerListeEleves(){
        List<Eleve> eleves = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            Query query = em.createQuery("select a from Eleve a order by a.nom ,a.prenom asc", Eleve.class);
           eleves = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return eleves;
    }
    public long compterTotalEleves() {
    EntityManager em = JpaUtil.obtenirContextePersistance();
    return em.createQuery("SELECT COUNT(e) FROM Eleve e", Long.class)
             .getSingleResult();
}

   
    
}
