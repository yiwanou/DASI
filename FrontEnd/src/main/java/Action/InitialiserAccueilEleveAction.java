/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import enums.StatutIntervention;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import service.Service;
import metier.Intervention;

/**
 *
 * @author gbakhat
 */
public class InitialiserAccueilEleveAction {

    public void execute(HttpServletRequest request) throws ParseException {
        Service service = new Service();
        HttpSession session = request.getSession(true);
        Eleve eleve = (Eleve) session.getAttribute("eleve");

        if (eleve != null) {
            request.setAttribute("intervention", false);
            List<Intervention> liste = service.obtenirInterventionsParEleve(eleve);
            if (liste != null) {
                for (Intervention i : liste) {
                    if (i.getStatut() == StatutIntervention.EN_COURS) {
                        request.setAttribute("intervention", true);
                    }
                }
            }
        }
    }
}
