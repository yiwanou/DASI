/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.Intervention;
import service.Service;

/**
 *
 * @author qdai
 */
public class VisioCommenceAction {

    public void execute(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession(true);
        Intervention intervention = (Intervention) session.getAttribute("intervention");

        Long duree = service.startVisio(intervention);
        if (duree != null) {
            request.setAttribute("isStart", true);
            request.setAttribute("duree", duree);
        } else {
            request.setAttribute("isStart", false);
        }

    }

}
