package crawler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is an implementation of the LinkDB interface.
 * 
 * @author James Hill
 */
public class LinkDBImpl implements LinkDB {
    
    Statement state;
    Connection conn;
    
    /**
     * This is the basic constructor for this class.
     * 
     * @param conn This is the database connection that will be used for this
     * query set.
     */
    public LinkDBImpl(Connection conn){
        try {
            this.conn = conn;
            state = conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            state.execute("CREATE TABLE Temp(Priority INTEGER, Link VARCHAR(5000))");
            state.execute("CREATE TABLE Results(Link VARCHAR(5000))");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Override
    public boolean checkExistsResult(String hyperlink){
        return false;
    }
    
    @Override
    public boolean checkExistsTemp(String hyperlink){
        return false;
    }
    
    @Override
    public void linkVisited(String hyperlink){
        
    }
    
    @Override
    public void writeResult(String hyperlink) {
        try {
            state = conn.createStatement();
            state.executeUpdate("INSERT INTO Results" +
                    " (Link)" +
                    " VALUES ('" + hyperlink + "')");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Override
    public void writeTemp(int priority, String hyperlink){
        try {
            state = conn.createStatement();
            state.executeUpdate("INSERT INTO Temp" +
                    " (Priority, Link)" +
                    " VALUES (" + priority + ", '" + hyperlink + "')");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
}