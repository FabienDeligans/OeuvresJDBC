/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public List<Oeuvre> lister(List<Oeuvre> lstOeuvres) throws Exception{

        PreparedStatement ps = null; 
        ResultSet rs = null; 
        Connection connection = null; 
        Oeuvre oeuvre = null;  
        Proprietaire proprio = null; 
        
        try {
            DbOutils dbOutils = new DbOutils(); 
            connection = dbOutils.connecter(); 
            ps = connection.prepareStatement("select * from oeuvre, proprietaire "
                    + "where oeuvre.id_proprietaire = proprietaire.id_proprietaire "); 
            rs = ps.executeQuery(); 
            
            while(rs.next()){
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
        } finally{
            try {
                if(ps != null){
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ajouter(String txtTitre, String txtPrix, String lProprietaires) throws Exception{

        PreparedStatement ps = null; 
        ResultSet rs = null; 
        Connection connection = null; 
        
        try {
            DbOutils dbOutils = new DbOutils(); 
            connection = dbOutils.connecter(); 

            ps = connection.prepareStatement("INSERT INTO oeuvre(id_proprietaire, titre, prix) VALUES (?, ?, ?)"); 
            ps.setString(1, lProprietaires);
            ps.setString(2, txtTitre);
            ps.setString(3, txtPrix);
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw e; 
        } finally{
            try {
                if(ps != null){
                    ps.close();
                }
                if(rs != null){
                    rs.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
