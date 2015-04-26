package crawler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

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
            try{state.execute("DROP TABLE Temp");} catch (Exception e) {};
            try{state.execute("DROP TABLE Results");} catch (Exception e) {};
            state.execute("CREATE TABLE Temp(Priority INTEGER, Link VARCHAR(5000))");
            state.execute("CREATE TABLE Results(Link VARCHAR(5000))");
        } catch (SQLException exc) {
            System.err.println("Error processing stream1: " + exc);
        }
    }
    
    @Override
    public boolean checkExistsResult(String link){
        try {
            ResultSet result;
            state = conn.createStatement();
            result = state.executeQuery("SELECT * FROM Results WHERE"
                    + " Link='" + link + "'");
            return result.next();
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
            return true;
        }
    }
    
    @Override
    public boolean checkExistsTemp(String link){
        try {
            ResultSet result;
            state = conn.createStatement();
            result = state.executeQuery("SELECT * FROM Temp WHERE"
                    + " Link='" + link + "'");
            return result.next();
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
            return true;
        }
    }
    
    @Override
    public String getNextURL(){
        String nextURL = "";
        try {
            ResultSet result;
            state = conn.createStatement();
            result = state.executeQuery("SELECT Link "
                    + "FROM Temp "
                    + "WHERE Priority>0");
            if(result.next()){
                nextURL = result.getString(1);
            }
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        return nextURL;
    }
    
    @Override
    public void linkVisited(String link){
        try {
            state = conn.createStatement();
            state.executeUpdate("UPDATE Temp "
                    + "SET Priority=0 "
                    + "WHERE Link='"+ link + "'");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Override
    public LinkedList<String> returnResults(){
        LinkedList<String> results = new LinkedList<>();
        try {
            ResultSet result;
            state = conn.createStatement();
            result = state.executeQuery("SELECT * "
                    + "FROM Results");
            while(result.next()){
                results.add(result.getString(1));
            }   
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        return results;
    }
    
    @Override
    public void writeResult(String link) {
        try {
            state = conn.createStatement();
            state.executeUpdate("INSERT INTO Results" +
                    " (Link)" +
                    " VALUES ('" + link + "')");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Override
    public void writeTemp(int priority, String link){
        try {
            state = conn.createStatement();
            state.executeUpdate("INSERT INTO Temp" +
                    " (Priority, Link)" +
                    " VALUES (" + priority + ", '" + link + "')");
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
}