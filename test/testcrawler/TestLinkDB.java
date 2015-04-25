package testcrawler;

import crawler.LinkDB;
import crawler.LinkDBImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a testing class for the implementation of the LinkDBImpl class.
 * 
 * @author James Hill
 */
public class TestLinkDB {
    
    // The connection to a database.
    Connection conn;
    
    // An object for passing commands to the database through.
    LinkDB dataBase;
    
    ResultSet result;
    
    Statement state;
    
    // Strings for the database implementation.
    String dbName = "testDB;";
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String protocol = "jdbc:derby:memory:";
    
    // Strings representing hyperlinks.
    String link1 = "https://wikileaks.org";
    String link2 = "http://www.google.com";
    String link3 = "https://wikileaks.org/index.en.html/index";
    
    @Before
    public void prepare(){
        // Setup database.
        try {            
            Class.forName(driver);
            conn = DriverManager.getConnection(protocol + dbName + "create=true");
            dataBase = new LinkDBImpl(conn);
        } catch (SQLException | ClassNotFoundException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void testWriteLinkToTempTable(){
        int rows = 0;
        
        // Write to database table.
        dataBase.writeTemp(1, link1);
        dataBase.writeTemp(2, link2);
        dataBase.writeTemp(3, link3);
            
        try {
            // Get temporary table count.
            state = conn.createStatement();
            result = state.executeQuery("SELECT COUNT(*) FROM Temp");
            result.next();
            rows = result.getInt(1);
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        // Test the size of the table.
        assertEquals("The count of rows is incorrect.", 3, rows);
        
        // Create strings for comparison.
        String check1 = "";
        String check2 = "";
        String check3 = "";
        
        // Extract the strings from the ResultSet.
        try {
            result = state.executeQuery("SELECT Link FROM Temp");
            result.next();
            check1 = result.getString(1);
            result.next();
            check2 = result.getString(1);
            result.next();
            check3 = result.getString(1);
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        // Test the returned strings.
        assertEquals("The links are not identical.", link1, check1);
        assertEquals("The links are not identical.", link2, check2);
        assertEquals("The links are not identical.", link3, check3);
    }
    
    @Test
    public void testWriteLinkToResultsTable(){
        int rows = 0;
        
        // Write to database table.
        dataBase.writeResult(link1);
        dataBase.writeResult(link2);
        dataBase.writeResult(link3);
            
        try {
            // Get temporary table count.
            state = conn.createStatement();
            result = state.executeQuery("SELECT COUNT(*) FROM Temp");
            result.next();
            rows = result.getInt(1);
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        // Test the size of the table.
        assertEquals("The count of rows is incorrect.", 3, rows);
        
        // Create strings for comparison.
        String check1 = "";
        String check2 = "";
        String check3 = "";
        
        // Extract the strings from the ResultSet.
        try {
            result = state.executeQuery("SELECT Results FROM Temp");
            result.next();
            check1 = result.getString(1);
            result.next();
            check2 = result.getString(1);
            result.next();
            check3 = result.getString(1);
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        // Test the returned strings.
        assertEquals("The links are not identical.", link1, check1);
        assertEquals("The links are not identical.", link2, check2);
        assertEquals("The links are not identical.", link3, check3);
    }
}