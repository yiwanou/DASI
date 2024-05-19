/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.Matiere;

/**
 *
 * @author psaad
 */
public class MatiereDao {
    public void create (Matiere matiere){
       JpaUtil.obtenirContextePersistance().persist(matiere);
    }
    public void supprimer(Matiere matiere) {
    EntityManager em = JpaUtil.obtenirContextePersistance();
    em.remove(em.merge(matiere));
}
    public void update(Matiere matiere){
       JpaUtil.obtenirContextePersistance().merge(matiere);
    }
public Matiere trouverMatiereparID(Long id) {
        Matiere resultat =null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            resultat = em.find(Matiere.class,id);  
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return resultat;
    }


    
    
    public Matiere trouverMatiereParNom(String nom) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Matiere matiere;
        try {
            TypedQuery<Matiere> query = em.createQuery("SELECT m FROM Matiere m WHERE m.nom = :nom", Matiere.class);
            query.setParameter("nom", nom);
            matiere= query.getSingleResult();
        } catch (NoResultException e) {
            matiere=null;
        } finally {
            
        }
        return matiere;
    }


    public static List<Matiere> donnerListeMatieres(){
        List<Matiere> matieres = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            Query query = em.createQuery("select a from Matiere a order by a.nom asc", Matiere.class);
           matieres = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return matieres;
    }
}
