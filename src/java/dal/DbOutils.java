package dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import javax.naming.*;
import javax.sql.DataSource;

/**
 *
 * @author Fabien
 */
public class DbOutils {

    public DbOutils() {
    }
    
    public Connection connecter() throws Exception{
        Context initCtx, envCtx; 
        DataSource ds; 
        Connection connection; 
        try {
            initCtx = new InitialContext(); 
            envCtx = (Context)initCtx.lookup("java:comp/env"); 
            ds = (DataSource)envCtx.lookup("jdbc/Oeuvres"); 
            connection = ds.getConnection(); 
            return connection; 
        } catch (Exception e) {
            throw e; 
        }
    }
    
    public int getidentifiant(String table) throws Exception{
        CallableStatement cs = null; 
        Connection connection = null; 
        try {
            connection = this.connecter(); 
            cs = connection.prepareCall("{ ? = call generer_pk(?) }"); 
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, table);
            cs.execute(); 
            return cs.getInt(1); 
            
        } catch (Exception e) {
            throw e; 
        } finally{
            try {
                if(cs != null){
                    cs.close();
                }
            } catch (Exception e) {
                throw e; 
            }
        }
    }
}
