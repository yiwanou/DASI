/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.Etablissement;
import util.EducNetApi;

/**
 *
 * @author psaad
 */
public class EtablissementDao {
    public Etablissement findById(Long idEtablissement) {
        return JpaUtil.obtenirContextePersistance().find(Etablissement.class, idEtablissement);// returns null if said etablissement is not in database
    }
    public Etablissement findByCode(String code){
        Etablissement resultat=null;
         try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            Query query = em.createQuery("select a from Etablissement a where a.code = :unCode", Etablissement.class);
            query.setParameter("unCode", code);
            resultat = (Etablissement) query.getSingleResult();
            
        } catch (Exception e) {
        }
        return resultat;
        
    }
    public Etablissement create(Etablissement etablissement) {
        JpaUtil.obtenirContextePersistance().persist(etablissement);
    return etablissement;
    }
    public List<Etablissement> getAllEtablissements() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Etablissement> query = em.createQuery("SELECT i FROM Etablissement i ORDER BY i.nom, i.prenom ASC", Etablissement.class);
        return query.getResultList();
    }
    public long compterTotalEtablissements() {
    EntityManager em = JpaUtil.obtenirContextePersistance();
    return em.createQuery("SELECT COUNT(et) FROM Etablissement et", Long.class)
             .getSingleResult();
}
    public double calculerIpsMoyenEtablissements() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Double resultat = em.createQuery("SELECT AVG(et.ips) FROM Etablissement et", Double.class)
                            .getSingleResult();
        return resultat != null ? resultat : 0;
}


}
