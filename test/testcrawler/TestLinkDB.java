package testcrawler;

import crawler.LinkDB;
import crawler.LinkDBImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        
        // Create strings for comparison.
        int priority1 = 0;
        int priority2 = 0;
        int priority3 = 0;
            
        // Extract the strings from the ResultSet.
        try {
            result = state.executeQuery("SELECT Priority FROM Temp");
            result.next();
            priority1 = result.getInt(1);
            result.next();
            priority2 = result.getInt(1);
            result.next();
            priority3 = result.getInt(1);
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        // Test the returned strings.
        assertEquals("The links are not identical.", 1, priority1);
        assertEquals("The links are not identical.", 2, priority2);
        assertEquals("The links are not identical.", 3, priority3);
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
            result = state.executeQuery("SELECT COUNT(*) FROM Results");
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
            result = state.executeQuery("SELECT Link FROM Results");
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
    public void testCheckExistsTemp(){
        // Write to database table.
        dataBase.writeTemp(1, link1);
        dataBase.writeTemp(2, link2);
        dataBase.writeTemp(3, link3);
        
        // Test for duplicates.
        boolean duplicate;
        duplicate = dataBase.checkExistsTemp(link1);
        assertTrue("The duplicate is not recognized.", duplicate);
        duplicate = dataBase.checkExistsTemp(link2);
        assertTrue("The duplicate is not recognized.", duplicate);
        duplicate = dataBase.checkExistsTemp(link3);
        assertTrue("The duplicate is not recognized.", duplicate);
    }
    
    @Test
    public void testCheckExistsTempFails(){
        // Write to database table.
        dataBase.writeTemp(1, link1);
        dataBase.writeTemp(2, link2);
        dataBase.writeTemp(3, link3);
        
        // Test for duplicates.
        boolean nonDupe;
        nonDupe = dataBase.checkExistsTemp("https://github.com/");
        assertFalse("The duplicate is not recognized.", nonDupe);
    }
}