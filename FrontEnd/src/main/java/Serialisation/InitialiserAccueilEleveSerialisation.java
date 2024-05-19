/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Eleve;

/**
 *
 * @author gbakhat
 */
public class InitialiserAccueilEleveSerialisation {

    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("ok");
        PrintWriter out = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        
        System.out.println(req.getAttribute("intervention"));
        container.addProperty("intervention", (Boolean) req.getAttribute("intervention"));
        
        res.setContentType("application/json;charset=UTF-8");
        out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
    }
}
