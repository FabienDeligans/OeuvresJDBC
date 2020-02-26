package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modeles.Adherent;
import modeles.Oeuvre;
import modeles.Reservation;
import outils.Utilitaire;

/**
 *
 * @author Prof
 */
public class ReservationDao {

    public ReservationDao() {
    }

    public List<Reservation> lister(List<Reservation> lstReservations) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Reservation resa = null;
        Adherent adh = null;
        Oeuvre oev = null;

        try {
            DbOutils dbOutils = new DbOutils();
            connection = dbOutils.connecter();
            ps = connection.prepareStatement("SELECT * FROM reservation, oeuvre, adherent "
                    + "where reservation.id_adherent = adherent.id_adherent "
                    + "and reservation.id_oeuvre = oeuvre.id_oeuvre; ");

            rs = ps.executeQuery();

            while (rs.next()) {
                adh = new Adherent();
                oev = new Oeuvre();
                resa = new Reservation();

                adh.setId_adherent(rs.getInt("id_adherent"));
                adh.setNom_adherent(rs.getString("nom_adherent"));
                adh.setPrenom_adherent(rs.getString("prenom_adherent"));

                oev.setId_oeuvre(rs.getInt("id_oeuvre"));
                oev.setTitre(rs.getString("titre"));

                resa.setAdherent(adh);
                resa.setOeuvre(oev);
                resa.setId_adherent(adh.getId_adherent());
                resa.setId_oeuvre(oev.getId_oeuvre());
                resa.setDate_reservation(rs.getDate("date_reservation"));
                resa.setStatut(rs.getString("statut"));

                lstReservations.add(resa);
            }
            return lstReservations;

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Reservation chercher(int id) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Reservation resa = null;
        Adherent adh = null;
        Oeuvre oev = null;

        try {
            DbOutils dbOutils = new DbOutils();
            connection = dbOutils.connecter();
            ps = connection.prepareStatement("SELECT * FROM reservation, oeuvre, adherent "
                    + "where reservation.id_adherent = adherent.id_adherent "
                    + "and reservation.id_oeuvre = oeuvre.id_oeuvre "
                    + "and oeuvre.id_oeuvre = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                adh = new Adherent();
                oev = new Oeuvre();
                resa = new Reservation();

                adh.setId_adherent(rs.getInt("adherent.id_adherent"));
                adh.setNom_adherent(rs.getString("adherent.nom_adherent"));
                adh.setPrenom_adherent(rs.getString("adherent.prenom_adherent"));

                oev.setId_oeuvre(rs.getInt("oeuvre.id_oeuvre"));
                oev.setTitre(rs.getString("oeuvre.titre"));

                resa.setAdherent(adh);
                resa.setOeuvre(oev);
                resa.setId_adherent(adh.getId_adherent());
                resa.setId_oeuvre(oev.getId_oeuvre());
                resa.setDate_reservation(rs.getDate("date_reservation"));
                resa.setStatut(rs.getString("statut"));

            }
            return resa;

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void enregistrer(String id_oeuvre, String id_adherent, String date) throws Exception{

        PreparedStatement ps = null;
        Connection connection = null;
        try {
            Utilitaire outi = new Utilitaire();
            Date datte = Utilitaire.StrToDate(date, "yyyy-MM-dd"); 
            
            DbOutils dbOutils = new DbOutils();
            connection = dbOutils.connecter();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement("insert into reservation "
                    + "(date_reservation, id_oeuvre, id_adherent, statut)"
                    + "values(?, ?, ?, ?");
            
            /**
             * 
             * PAS au point la gestion des dates !
             * 
             */
            ps.setDate(1, (java.sql.Date) datte);
            ps.setInt(2, Integer.parseInt(id_oeuvre));
            ps.setInt(3, Integer.parseInt(id_adherent));
            ps.setString(4, "Attente");
            
            ps.executeUpdate(); 
            connection.commit(); 

        } catch (Exception e) {
            connection.rollback();
            throw e; 
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

    }

}
