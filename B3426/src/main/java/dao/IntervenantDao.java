package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.Intervenant;

public class IntervenantDao {
    public void create(Intervenant intervenant) {
        JpaUtil.obtenirContextePersistance().persist(intervenant);
    }

    public Intervenant update(Intervenant intervenant) {
        return JpaUtil.obtenirContextePersistance().merge(intervenant);
    }

    public Intervenant findByMail(String mail) {
        Intervenant resultat = null;
        try {
            EntityManager em = JpaUtil.obtenirContextePersistance();
            Query query = em.createQuery("select a from Intervenant a where a.mail = :unMail", Intervenant.class);
            query.setParameter("unMail", mail);
            resultat = (Intervenant) query.getSingleResult();
            
        } catch (Exception e) {
                
        }
        return resultat;
    }
    public Intervenant findById(Long idIntervenant) {
        return JpaUtil.obtenirContextePersistance().find(Intervenant.class, idIntervenant);// returns null if said etablissement is not in database
    }

    public List<Intervenant> getAllIntervenants() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Intervenant> query = em.createQuery("SELECT i FROM Intervenant i ORDER BY i.nom, i.prenom ASC", Intervenant.class);
        return query.getResultList();
    }

    public List<Intervenant> trouverTousIntervenantsDisponibles() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Intervenant> query = em.createQuery("SELECT i FROM Intervenant i WHERE i.disponible = true", Intervenant.class);
        return query.getResultList();
    }
    
    public Intervenant trouverIntervenantDisponibleParNiveau(int niveau) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        List<Intervenant> intervenants = trouverTousIntervenantsDisponibles(); // Utiliser la méthode existante pour obtenir tous les intervenants disponibles

        // Filtrer manuellement la liste pour trouver les intervenants dont le niveau est dans la plage spécifiée
        Intervenant intervenantSelectionne = intervenants.stream()
            .filter(i -> niveau >= i.getNiveauMin() && niveau <= i.getNiveauMax())
            .min((i1, i2) -> Integer.compare(i1.getInterventionsIntervenant().size(), i2.getInterventionsIntervenant().size()))
            .orElse(null); // Sélectionner l'intervenant avec le moins d'interventions

        return intervenantSelectionne;
    }
    public List<Intervenant> trouverIntervenantsParNiveau(int niveauEleve) {
    EntityManager em = JpaUtil.obtenirContextePersistance();
    return em.createQuery(
            "SELECT i FROM Intervenant i WHERE i.niveauMin <= :niveau AND i.niveauMax >= :niveau AND i.disponible = true",
            Intervenant.class)
            .setParameter("niveau", niveauEleve)
            .getResultList();
}

}

