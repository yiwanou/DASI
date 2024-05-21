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
public class VisioTermineAction {


    public void execute(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession(true);
        Intervention intervention = (Intervention) session.getAttribute("intervention");
        service.endVisio(intervention);
        
        if (intervention != null) {
            request.setAttribute("isEnd", true);
            request.setAttribute("intervention", intervention);
        } else {
            request.setAttribute("isEnd", false);
        }

    }

}
    

