/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modeles.Proprietaire;

/**
 *
 * @author Fabien
 */
public class ProprietaireDao {

    public ProprietaireDao() {
    }

    public Proprietaire connecter(String login, String pwd) throws Exception{

        PreparedStatement ps = null; 
        ResultSet rs = null; 
        Connection connection = null; 
        Proprietaire proprio = null;  
        
        try {
            DbOutils dbOutils = new DbOutils(); 
            connection = dbOutils.connecter(); 
            ps = connection.prepareStatement("select * from proprietaire where login = ? "); 
            ps.setString(1, login);
            rs = ps.executeQuery(); 
            
            if(rs.next()){
                if(pwd.equalsIgnoreCase(rs.getString("pwd"))){
                    proprio = new Proprietaire(); 
                    proprio.setId_proprietaire(rs.getInt("id_proprietaire"));
                }
            }
            return proprio; 
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
