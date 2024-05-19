package Controleur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Action.AuthentifierUtilisateurAction;
import test.TestUtilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Serialisation.*;
import Action.*;
import dao.JpaUtil;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Eleve;

/**
 *
 * @author qdai
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
        } catch (ParseException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
        } catch (ParseException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        init();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String todo = request.getParameter("todo");

            System.out.println("Trace : todo = " + todo);

            switch (todo) {
                case "connecter": {
                    String utilisateur = request.getParameter("interface");
                    System.out.println("Trace : utilisateur = " + utilisateur);
                    switch (utilisateur) {
                        case "eleve": {
                            new AuthentifierEleveAction().execute(request);
                            Eleve eleve = (Eleve) request.getAttribute("utilisateur");
                            if (eleve != null) {
                                System.out.println("eleve existant");
                            } else {
                                System.out.println("eleve inexistant");
                            }
                            new ProfilEleveSerialisation().appliquer(request, response);
                            break;
                        }
                    }
                    break;
                }
                case "inscrire": {
                    new InscrireEleveAction().execute(request);
                    Boolean inscription = (Boolean) request.getAttribute("inscription");
                    if (inscription == true) {
                        System.out.println("eleve inscrit");
                    } else {
                        System.out.println("erreur d'inscription");
                    }
                    new InscrireEleveSerialisation().appliquer(request, response);
                    break;
                }
                case "accueilEleve": {
                    new InitialiserAccueilEleveAction().execute(request);
                    Boolean intervention = (Boolean) request.getAttribute("intervention");
                    if (intervention == true) {
                        System.out.println("initialisation réussie");
                    } else {
                        System.out.println("erreur d'initialisation");
                    }
                    new InitialiserAccueilEleveSerialisation().appliquer(request, response);
                    break;
                }
                case "chargerListeMatieres": {
                    new ChargerMatieresAction().execute(request);
                    Boolean marche = (Boolean) request.getAttribute("marche");
                    if (marche == true) {
                        System.out.println("chargement des matières réussi");
                    } else {
                        System.out.println("erreur de chargement des matières");
                    }
                    new ChargerMatieresSerialisation().appliquer(request, response);
                    break;
                }
                case "demanderIntervention": {
                    new DemandeInterventionAction().execute(request);
                    Boolean accepte = (Boolean) request.getAttribute("accepte");
                    if (accepte == true) {
                        System.out.println("Intervenant trouvé");
                    } else {
                        System.out.println("Pas d'intervenant trouvé");
                    }
                    new DemandeInterventionSerialisation().appliquer(request, response);
                    break;
                }
                case "historiqueEleve": {
                    new HistoriqueEleveAction().execute(request);
                    new HistoriqueEleveSerialisation().appliquer(request, response);
                    break;
                }
            }

        }
    }

//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>

    public void init() throws ServletException {
        JpaUtil.creerFabriquePersistance();
    }

//    @Override
//        public void destroy() throws ServletException {
//            JpaUtil.fermerFabriquePersistance();
//        }
}
