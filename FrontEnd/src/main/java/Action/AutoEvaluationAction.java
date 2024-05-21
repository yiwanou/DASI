/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Eleve;
import service.Service;

/**
 *
 * @author qdai
 */
public class AutoEvaluationAction {

    public void execute(HttpServletRequest request) throws ParseException {
        Service service = new Service();

        Integer note = Integer.parseInt(request.getParameter("note"));
        HttpSession session = request.getSession(true);
        Eleve eleve = (Eleve) session.getAttribute("eleve");

        Boolean isNoter = service.noterIntervention(eleve, note);

        System.out.println(note);

        if (isNoter) {
            request.setAttribute("note", note);
            request.setAttribute("noter", false);
        } else {
            request.setAttribute("note", null);
            request.setAttribute("noter", false);
        }

    }
}
