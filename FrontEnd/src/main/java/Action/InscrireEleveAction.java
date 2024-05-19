/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import service.Service;

/**
 *
 * @author qdai
 */
public class InscrireEleveAction {
    public void execute(HttpServletRequest request) throws ParseException {
        Service service = new Service();
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String mail = request.getParameter("mail");
        
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date naissance = null; // Initialize the Date object

        String dateString = request.getParameter("naissance");
        if (dateString != null && !dateString.isEmpty()) {
            naissance = inputFormat.parse(dateString);
        }

                
        Integer niveau = Integer.parseInt(request.getParameter("niveau"));
        String code = request.getParameter("code");
        String mdp = request.getParameter("mdp");
       
        Eleve eleve = new Eleve(nom, prenom, mail, naissance, niveau);
        
        
        Eleve eleveInscrit = service.inscrireEleve(eleve, code, mdp);
        
        System.out.println(eleveInscrit);
        
        if (eleveInscrit !=  null) {
            request.setAttribute("utilisateur", eleve);
            request.setAttribute("inscription", true);
            HttpSession session = request.getSession(true);
            session.setAttribute("eleve", eleveInscrit);
        }
        else{
            request.setAttribute("utilisateur", null);
            request.setAttribute("inscription", false);
        }
        
    }
}
