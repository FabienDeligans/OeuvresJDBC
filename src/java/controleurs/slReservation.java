package controleurs;

import dal.AdherentDao;
import dal.OeuvreDao;
import dal.ProprietaireDao;
import dal.ReservationDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import outils.*;
import modeles.*;

/**
 *
 * @author alain
 */
public class slReservation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String erreur, titre, date, vueReponse;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String demande;
        vueReponse = "/home.jsp";
        erreur = "";
        try {
            demande = getDemande(request);
            if (demande.equalsIgnoreCase("reserver.res")) {
                vueReponse = reserverOeuvre(request);
            } else if (demande.equalsIgnoreCase("enregistrerReservation.res")) {
                vueReponse = enregistrerReservation(request);
            } else if (demande.equalsIgnoreCase("listeReservations.res")) {
                vueReponse = listeReservations(request);
            } else if (demande.equalsIgnoreCase("confirmerReservation.res")) {
                vueReponse = confirmerReservation(request);
            } else if (demande.equalsIgnoreCase("supprimerReservation.res")) {
                vueReponse = supprimerReservation(request);
            }
        } catch (Exception e) {
            erreur = e.getMessage();
        } finally {
            request.setAttribute("erreurR", erreur);
            request.setAttribute("pageR", vueReponse);
            RequestDispatcher dsp = request.getRequestDispatcher("/index.jsp");
            if (vueReponse.contains(".res")) {
                dsp = request.getRequestDispatcher(vueReponse);
            }
            dsp.forward(request, response);
        }
    }

    /**
     * Transforme dans la base de données une réservation en Attente en une
     * réservation Confirmée
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String confirmerReservation(HttpServletRequest request) throws Exception {
        String id, date; 
        try {

            id = request.getParameter("id"); 
            date = request.getParameter("date");
            
            ReservationDao resaDao = new ReservationDao(); 
            resaDao.update(id, date); 
            
            return ("listeReservations.res");
        } catch (Exception e) {
            throw e;
        }
    }

    private String supprimerReservation(HttpServletRequest request) throws Exception {
        String id, date; 
        try {
            
            id = request.getParameter("id"); 
            date = request.getParameter("date");
            
            ReservationDao resaDao = new ReservationDao(); 
            resaDao.supprimer(id, date); 
            
            return ("listeReservations.res");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * liste des réservations en Attente
     *
     * @param request
     * @return String page de redirection
     * @throws Exception
     */
    private String listeReservations(HttpServletRequest request) throws Exception {

        try {

            vueReponse = "/index.jsp";

            HttpSession session = request.getSession(false);
            int id = (Integer) session.getAttribute("userId");

            Proprietaire proprio = new Proprietaire();
            proprio.setId_proprietaire(id);

            if (proprio != null) {
                ReservationDao resaDao = new ReservationDao();
                List<Reservation> lstReservations = new ArrayList();
                lstReservations = resaDao.lister(lstReservations);
                request.setAttribute("lstReservationsR", lstReservations);
            }

            return ("/listereservations.jsp");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Enregistre une réservation et la met en Attente
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String enregistrerReservation(HttpServletRequest request) throws Exception {
        String id_oeuvre;
        String id_adherent;
        String date;

        id_oeuvre = request.getParameter("id_oeuvre");
        id_adherent = request.getParameter("lstAdherents");
        date = request.getParameter("txtDate");
        titre = request.getParameter("txtTitre");
        
        try {

            ReservationDao resaDao = new ReservationDao();
            resaDao.enregistrer(id_oeuvre, id_adherent, date);

            return ("listeReservations.res");
        } catch (Exception e) {
            erreur = e.getMessage();
            if (erreur.contains("PRIMARY")) {
                erreur = "L'oeuvre " + titre + " a déjà été réservée pour le : " + date + " !";
            }
            throw new Exception(erreur);
        }
    }

    /**
     * Lit une oeuvre, l'affiche et initialise la liste des adhérents pour
     * pouvoir saisir une réservation : Saisie date et sélection de l'adhérent
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String reserverOeuvre(HttpServletRequest request) throws Exception {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            OeuvreDao oeuvreDao = new OeuvreDao();
            Oeuvre oeuvre = new Oeuvre();
            oeuvre = oeuvreDao.rechercher(id);

            Reservation resa = new Reservation();
            resa.setId_oeuvre(id);
            resa.setOeuvre(oeuvre);

            request.setAttribute("resaR", resa);
            request.setAttribute("oeuvreR", oeuvre);

            List<Adherent> lAdherents = new ArrayList();
            AdherentDao adhDao = new AdherentDao();
            lAdherents = adhDao.lister(lAdherents);
            request.setAttribute("lAdherentsR", lAdherents);

            return ("/reservation.jsp");
        } catch (Exception e) {
            throw e;
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
