/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleurs;

import dal.OeuvreDao;
import dal.ProprietaireDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modeles.Oeuvre;
import modeles.Proprietaire;

/**
 *
 * @author arsane
 */
public class slOeuvres extends HttpServlet {

    private String erreur, titre, vueReponse;

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
            throws ServletException, IOException {
        String demande;
        String vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("login.oe")) {
                vueReponse = login(request);
            } else if (demande.equalsIgnoreCase("connecter.oe")) {
                vueReponse = connecter(request);
            } else if (demande.equalsIgnoreCase("deconnecter.oe")) {
                vueReponse = deconnecter(request);
            } else if (demande.equalsIgnoreCase("ajouter.oe")) {
                vueReponse = creerOeuvre(request);
            } else if (demande.equalsIgnoreCase("enregistrer.oe")) {
                vueReponse = enregistrerOeuvre(request);
            } else if (demande.equalsIgnoreCase("modifier.oe")) {
                vueReponse = modifierOeuvre(request);
            } else if (demande.equalsIgnoreCase("catalogue.oe")) {
                vueReponse = listerOeuvres(request);
            } else if (demande.equalsIgnoreCase("supprimer.oe")) {
                vueReponse = supprimerOeuvre(request);
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse);
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".oe")) {
                dsp = request.getRequestDispatcher(vueReponse);
            }
            dsp.forward(request, response);
        }
    }

    /**
     * Enregistre une oeuvre qui a été soit créée (id_oeuvre = 0) soit modifiée
     * (id_oeuvre > 0)
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String enregistrerOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse = "/ajouter.oe";
        String txtTitre, txtPrix, lProprietaires;
        try {
            HttpSession session = request.getSession(false);
            int id = (Integer) session.getAttribute("userId");

            Proprietaire proprio = new Proprietaire();
            proprio.setId_proprietaire(id);

            if (proprio.getId_proprietaire() == 1) {

                txtTitre = request.getParameter("txtTitre");
                txtPrix = request.getParameter("txtPrix");
                lProprietaires = request.getParameter("lProprietaires");

                OeuvreDao oeuvreDao = new OeuvreDao();

                if (txtTitre != null && txtPrix != null && lProprietaires != null) {
                    oeuvreDao.ajouter(txtTitre, txtPrix, lProprietaires);
                    
                    vueReponse = listerOeuvres(request);
                }
                
            }
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Lit et affiche une oeuvre pour pouvoir la modifier
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String modifierOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse;
        try {

            vueReponse = "/oeuvre.jsp";
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Supprimer une oeuvre
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String supprimerOeuvre(HttpServletRequest request) throws Exception {
        String vueReponse;
        try {

            vueReponse = "catalogue.oe";
            return (vueReponse);
        } catch (Exception e) {
            erreur = e.getMessage();
            if (erreur.contains("FK_RESERVATION_OEUVRE")) {
                erreur = "Il n'est pas possible de supprimer l'oeuvre : " + titre + " car elle a été réservée !";
            }
            throw new Exception(erreur);
        }
    }

    /**
     * Affiche le formulaire vide d'une oeuvre Initialise la liste des
     * propriétaires Initialise le titre de la page
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String creerOeuvre(HttpServletRequest request) throws Exception {

        String vueReponse = "/index.jsp";
        try {

            HttpSession session = request.getSession(false);
            int id = (Integer) session.getAttribute("userId");

            Proprietaire proprio = new Proprietaire();
            proprio.setId_proprietaire(id);

            if (proprio.getId_proprietaire() == 1) {

                List<Proprietaire> lProprietaires = new ArrayList();
                ProprietaireDao proprioDao = new ProprietaireDao();
                lProprietaires = proprioDao.lister(lProprietaires);

                request.setAttribute("lProprietairesR", lProprietaires);
                vueReponse = "/oeuvre.jsp";

            }
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Vérifie que l'utilisateur a saisi le bon login et mot de passe
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String connecter(HttpServletRequest request) throws Exception {
        String vueReponse = "/login.oe";
        try {
            String login, pwd;
            ProprietaireDao proprioDao = new ProprietaireDao();
            Proprietaire proprio;

            login = request.getParameter("txtLogin");
            pwd = request.getParameter("txtPwd");

            proprio = proprioDao.connecter(login, pwd);
            if (proprio != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", proprio.getId_proprietaire());
                vueReponse = "/home.jsp";
            }
            return (vueReponse);
        } catch (Exception e) {
            throw e;
        }
    }

    private String deconnecter(HttpServletRequest request) throws Exception {
        try {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", null);
            return ("/home.jsp");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Afficher la page de login
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String login(HttpServletRequest request) throws Exception {
        try {
            return ("/login.jsp");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * liste des oeuvres pour le catalogue
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listerOeuvres(HttpServletRequest request) throws Exception {

        try {
            vueReponse = "/index.jsp";

            HttpSession session = request.getSession(false);
            int id = (Integer) session.getAttribute("userId");

            Proprietaire proprio = new Proprietaire();
            proprio.setId_proprietaire(id);

            if (proprio != null) {
                OeuvreDao oeuvreDao = new OeuvreDao();
                List<Oeuvre> lstOeuvres = new ArrayList();
                lstOeuvres = oeuvreDao.lister(lstOeuvres);
                request.setAttribute("lstOeuvresR", lstOeuvres);
                vueReponse = ("/catalogue.jsp");
            }

        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            return vueReponse;
        }
    }

    /**
     * Extrait le texte de la demande de l'URL
     *
     * @param request
     * @return String texte de la demande
     */
    private String getDemande(HttpServletRequest request) {
        String demande = "";
        demande = request.getRequestURI();
        demande = demande.substring(demande.lastIndexOf("/") + 1);
        return demande;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
