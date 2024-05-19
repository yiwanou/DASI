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
 * @author qdai
 */
public class InscrireEleveSerialisation {
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();

        container.addProperty("inscription", (Boolean) req.getAttribute("inscription"));

        Eleve eleve = (Eleve) req.getAttribute("utilisateur");
        if (eleve != null) {
            JsonObject jsonEleve = new JsonObject();
            jsonEleve.addProperty("id", (Long) eleve.getIdEleve());
            jsonEleve.addProperty("nom", (String) eleve.getNom());
            jsonEleve.addProperty("prenom", (String) eleve.getPrenom());
            jsonEleve.addProperty("mail", (String) eleve.getMail());

            container.add("eleve", jsonEleve);

        }

        res.setContentType("application/json;charset=UTF-8");
        out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();
    }
}
