package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
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

    public List<Proprietaire> lister(List<Proprietaire> lProprietaires) throws Exception{

        PreparedStatement ps = null; 
        ResultSet rs = null; 
        Connection connection = null; 
        Proprietaire proprio = null;  
        
        try {
            DbOutils dbOutils = new DbOutils(); 
            connection = dbOutils.connecter(); 
            ps = connection.prepareStatement("select * from proprietaire"); 
            rs = ps.executeQuery(); 
            
            while(rs.next()){
                proprio = new Proprietaire(); 
                proprio.setId_proprietaire(rs.getInt("id_proprietaire"));
                proprio.setNom_proprietaire(rs.getString("nom_proprietaire"));
                proprio.setPrenom_proprietaire(rs.getString("prenom_proprietaire"));
                
                lProprietaires.add(proprio); 
            }
            return lProprietaires; 
            
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
