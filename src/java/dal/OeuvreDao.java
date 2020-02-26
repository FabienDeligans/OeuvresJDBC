package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import modeles.Oeuvre;
import modeles.Proprietaire;

/**
 *
 * @author Fabien
 */
public class OeuvreDao {

    public OeuvreDao() {
    }

    public List<Oeuvre> lister(List<Oeuvre> lstOeuvres) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        Oeuvre oeuvre = null;
        Proprietaire proprio = null;

        try {
            DbOutils dbOutils = new DbOutils();
            connection = dbOutils.connecter();
            ps = connection.prepareStatement("select * from oeuvre, proprietaire "
                    + "where oeuvre.id_proprietaire = proprietaire.id_proprietaire "
                    + "order by id_oeuvre");
            rs = ps.executeQuery();

            while (rs.next()) {
                proprio = new Proprietaire();

                proprio.setNom_proprietaire(rs.getString("nom_proprietaire"));
                proprio.setPrenom_proprietaire(rs.getString("prenom_proprietaire"));

                oeuvre = new Oeuvre();

                oeuvre.setId_oeuvre(rs.getInt("id_oeuvre"));
                oeuvre.setPrix(rs.getDouble("prix"));
                oeuvre.setTitre(rs.getString("titre"));
                oeuvre.setId_proprietaire(rs.getInt("oeuvre.id_proprietaire"));
                oeuvre.setProprietaire(proprio);

                lstOeuvres.add(oeuvre);
            }
            return lstOeuvres;
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

    public void ajouter(String txtTitre, String txtPrix, String lProprietaires) throws Exception {

        PreparedStatement ps = null;
        Connection connection = null;
        Oeuvre oeuvre = new Oeuvre();
        try {
            DbOutils dbOutils = new DbOutils();
            connection = dbOutils.connecter();
            
            
            /**
             * 
             * retravailler si F5 ça revoie les donénes du form
             * 
             */
            
            
            
            connection.setAutoCommit(false);;

            oeuvre.setId_oeuvre(dbOutils.getidentifiant("oeuvre"));
            oeuvre.setId_proprietaire(Integer.parseInt(lProprietaires));
            oeuvre.setPrix(Double.parseDouble(txtPrix));
            oeuvre.setTitre(txtTitre);

            ps = connection.prepareStatement("INSERT INTO oeuvre(id_proprietaire, titre, prix, id_oeuvre) VALUES (?, ?, ?, ?)");
            ps.setInt(1, oeuvre.getId_proprietaire());
            ps.setString(2, oeuvre.getTitre());
            ps.setDouble(3, oeuvre.getPrix());
            ps.setInt(4, oeuvre.getId_oeuvre());

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

    public Oeuvre rechercher(int id) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            DbOutils dbOutils = new DbOutils();
            connection = dbOutils.connecter();

            ps = connection.prepareStatement("select * from oeuvre, proprietaire where id_oeuvre = ? and oeuvre.id_proprietaire = proprietaire.id_proprietaire");
            ps.setInt(1, id);

            rs = ps.executeQuery();
            Oeuvre oeuvre = new Oeuvre();

            if (rs.next()) {
                oeuvre.setId_oeuvre(rs.getInt("oeuvre.id_oeuvre"));
                oeuvre.setId_proprietaire(rs.getInt("oeuvre.id_proprietaire"));
                oeuvre.setPrix(rs.getDouble("prix"));
                oeuvre.setTitre(rs.getString("titre"));

                Proprietaire proprio = new Proprietaire();
                proprio.setId_proprietaire(rs.getInt("oeuvre.id_proprietaire"));
                proprio.setNom_proprietaire(rs.getString("nom_proprietaire"));
                proprio.setPrenom_proprietaire(rs.getString("prenom_proprietaire"));

                oeuvre.setProprietaire(proprio);

            }
            return oeuvre;
        } catch (Exception e) {
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

    public void supprimer(int id) throws Exception{

        PreparedStatement ps = null; 
        ResultSet rs = null; 
        Connection connection = null; 
        DbOutils dbOutils = null; 
        
        try {
            dbOutils = new DbOutils(); 
            connection = dbOutils.connecter(); 
            ps = connection.prepareStatement("delete from oeuvre where id_oeuvre = ?"); 
            ps.setInt(1, id);
            
            ps.executeUpdate(); 
            
        } catch (Exception e) {
        }
    }

}
