package crawler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is an implementation of the LinkDB interface.
 * 
 * @author James Hill
 */
public class LinkDBImpl implements LinkDB {
    
    Statement state;
    
    /**
     * This is the basic constructor for this class.
     * 
     * @param conn This is the database connection that will be used for this
     * query set.
     */
    public LinkDBImpl(Connection conn){
        try {
            state = conn.createStatement();
            state.execute("CREATE TABLE Temp(Priority INTEGER, Link VARCHAR(5000)");
            state.execute("CREATE TABLE Results(Link VARCHAR(5000)");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Override
    public boolean checkDuplicateResult(String hyperlink){
        return false;
    }
    
    @Override
    public boolean checkDuplicateTemp(String hyperlink){
        return false;
    }
    
    @Override
    public void linkVisited(String hyperlink){
        
    }
    
    @Override
    public void writeResult(String hyperlink){
        
    }
    
    @Override
    public void writeTemp(int priority, String hyperlink){
        
    }
}