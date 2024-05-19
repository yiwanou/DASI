/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import metier.Intervention;
import metier.Matiere;
import service.Service;

/**
 *
 * @author gbakhat
 */
public class DemandeInterventionAction {
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        
        HttpSession session = request.getSession(true);
        Eleve eleve = (Eleve) session.getAttribute("eleve");
        Matiere matiere = (Matiere) request.getAttribute("matiere");
        String commentaire = (String) request.getAttribute("description");
        Intervention intervention = service.envoyerDemande(eleve, matiere, commentaire);
        
        request.setAttribute("accepte", false);
        
        if(intervention != null){
            request.setAttribute("accepte", true);
            session.setAttribute("intervention", intervention);
        }
        
    }
}
