package Action;


import Action.Action;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import service.Service;
import test.TestUtilisateur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author qdai
 */
public class AuthentifierUtilisateurAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        TestUtilisateur utilisateur = new TestUtilisateur(1024L, "Lovelace", "Ada", "ada.lovelace@insa-lyon.fr");
//service.Service service = new Service();

        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        System.out.println("Trace : login = " + login);
        if (Objects.equals(utilisateur.getMail(), login)) {
            request.setAttribute("utilisateur", utilisateur);
            request.setAttribute("connexion", true);
        }
        else{
            request.setAttribute("utilisateur", null);
            request.setAttribute("connexion", false);
        }
        
    }

}
