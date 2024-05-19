/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import service.Service;
import test.TestUtilisateur;

/**
 *
 * @author qdai
 */
public class AuthentifierEleveAction {
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        
        Eleve eleve = service.authentificationEleve(login, mdp);
        
        System.out.println("Trace : login = " + login);
        System.out.println(eleve);
        
        if (eleve !=  null) {
            request.setAttribute("utilisateur", eleve);
            request.setAttribute("connexion", true);
            HttpSession session = request.getSession(true);
            session.setAttribute("eleve", eleve);
    
        }
        else{
            request.setAttribute("utilisateur", null);
            request.setAttribute("connexion", false);
        }
        
    }
}
