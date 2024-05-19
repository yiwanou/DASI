/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import service.Service;
import metier.Intervention;

/**
 *
 * @author qdai
 */
public class HistoriqueEleveAction {

    public void execute(HttpServletRequest request) throws ParseException {
        Service service = new Service();
        HttpSession session = request.getSession(true);
        Eleve eleve = (Eleve) session.getAttribute("eleve");

        if (eleve != null) {
            List<Intervention> liste = service.obtenirInterventionsParEleve(eleve);
            request.setAttribute("eleveHistorique", liste);

        }
    }
}
