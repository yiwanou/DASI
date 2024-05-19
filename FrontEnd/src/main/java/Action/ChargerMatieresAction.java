/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import metier.Matiere;
import service.Service;

/**
 *
 * @author gbakhat
 */
public class ChargerMatieresAction {
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        List<Matiere> matieres = service.obtenirListeMatieres();
        
        request.setAttribute("marche", false);
        
        if(matieres != null){
            request.setAttribute("marche", true);
            request.setAttribute("matieres", matieres);
        }
        
    }
}
