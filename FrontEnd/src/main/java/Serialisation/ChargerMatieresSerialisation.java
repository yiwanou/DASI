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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.Matiere;

/**
 *
 * @author gbakhat
 */
public class ChargerMatieresSerialisation {
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = null;
        
        List<Matiere> listeMatieres = (List<Matiere>)req.getAttribute("matieres");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        
        Boolean marche = (Boolean) req.getAttribute("marche");
        container.addProperty("marche", (Boolean)req.getAttribute("marche"));
        
        if (marche){
            JsonArray jsonListeMatieres = new JsonArray();
            for (Matiere matiere : listeMatieres) {
                JsonObject jsonMatiere = new JsonObject();
                jsonMatiere.addProperty("id", matiere.getIdMatiere());
                jsonMatiere.addProperty("nom", matiere.getNom());
                jsonListeMatieres.add(jsonMatiere);
            }
            container.add("matieres", jsonListeMatieres);
        }

        res.setContentType("application/json;charset=UTF-8");
        out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
    }
}
