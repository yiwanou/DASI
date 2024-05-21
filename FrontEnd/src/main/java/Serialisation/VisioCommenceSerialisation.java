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

/**
 *
 * @author qdai
 */
public class VisioCommenceSerialisation {

    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();


        Boolean isStart = (Boolean)req.getAttribute("isStart");
         container.addProperty("isStart", isStart);
        
        if (isStart) {
            container.addProperty("duree", (Long) req.getAttribute("duree"));

        }

        res.setContentType("application/json;charset=UTF-8");
        out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();

    }
}