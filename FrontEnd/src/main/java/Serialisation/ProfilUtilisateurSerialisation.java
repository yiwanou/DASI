package Serialisation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ProfilUtilisateurSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = null;

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();

        container.addProperty("connexion", (Boolean) req.getAttribute("connexion"));

        TestUtilisateur utilisateur = (TestUtilisateur) req.getAttribute("utilisateur");
        if (utilisateur != null) {
            JsonObject jsonUtilisateur = new JsonObject();
            jsonUtilisateur.addProperty("id", (Long) utilisateur.getId());
            jsonUtilisateur.addProperty("nom", (String) utilisateur.getNom());
            jsonUtilisateur.addProperty("prenom", (String) utilisateur.getPrenom());
            jsonUtilisateur.addProperty("mail", (String) utilisateur.getMail());

            container.add("utilisateur", jsonUtilisateur);

        }

        res.setContentType("application/json;charset=UTF-8");
        out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();

    }
}
