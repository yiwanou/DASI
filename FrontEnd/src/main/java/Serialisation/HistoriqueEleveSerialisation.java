/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Intervention;

/**
 *
 * @author qdai
 */
public class HistoriqueEleveSerialisation {

    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();

         List<Intervention> liste = (List<Intervention>) req.getAttribute("eleveHistorique");

        if (liste != null) {
            JsonArray jsonListeInterventions = new JsonArray();
            for (Intervention intervention  : liste) {
                JsonObject jsonintervention = new JsonObject();
//                jsonintervention.addProperty("date", intervention.getDate());
                jsonintervention.addProperty("intervenant", intervention.getIntervenant().getNom());
                jsonintervention.addProperty("matiere", intervention.getMatiere().getNom());
                
                jsonListeInterventions.add(jsonintervention);
            }
            container.add("intervention", jsonListeInterventions);
        }

        res.setContentType("application/json;charset=UTF-8");
        out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();

    }
}
