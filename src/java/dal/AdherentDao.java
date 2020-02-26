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
import modeles.Adherent;

/**
 *
 * @author Admin
 */
public class AdherentDao {

    public AdherentDao() {
    }
    
    public List<Adherent> lister(List<Adherent> lAdherents) throws Exception{

        PreparedStatement ps = null; 
        ResultSet rs = null; 
        Connection connection = null; 
        Adherent adh = null;  
        
        try {
            DbOutils dbOutils = new DbOutils(); 
            connection = dbOutils.connecter(); 
            ps = connection.prepareStatement("select * from adherent"); 
            rs = ps.executeQuery(); 
            
            while(rs.next()){
                adh = new Adherent(); 
                adh.setId_adherent(rs.getInt("id_Adherent"));
                adh.setNom_adherent(rs.getString("nom_Adherent"));
                adh.setPrenom_adherent(rs.getString("prenom_Adherent"));
                
                lAdherents.add(adh); 
            }
            return lAdherents; 
            
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
