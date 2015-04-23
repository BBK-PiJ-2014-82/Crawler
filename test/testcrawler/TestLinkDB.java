package testcrawler;

import crawler.LinkDB;
import crawler.LinkDBImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
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
    
    Statement state;
    
    // Strings for the database implementation.
    String dbName = "testDB;";
    String protocol = "jdbc:derby:";
    
    // Strings representing hyperlinks.
    String link1 = "https://wikileaks.org";
    String link2 = "http://www.google.com";
    String link3 = "https://wikileaks.org/index.en.html/index";
    
    @Before
    public void setup(){
        try {
            // Setup database.
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver);
            conn = DriverManager.getConnection(protocol + dbName);
            dataBase = new LinkDBImpl(conn);
            
            state = conn.createStatement();
            
        } catch (SQLException | ClassNotFoundException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @After
    public void close(){
        try {
            conn.close();
        } catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void testWriteLinkToResultsTable(){
        int rows = 0;
        ResultSet result;
        
        // Write to database table.
        dataBase.writeTemp(1, link1);
        dataBase.writeTemp(2, link2);
        dataBase.writeTemp(3, link3);
        
        try {
            // Get temporary table count.
            result = state.executeQuery("SELECT COUNT(*) FROM Temp");
            rows = result.getInt("rowcount");
        } catch (SQLException ex) {
            Logger.getLogger(TestLinkDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Test the size of the table.
        assertEquals("The count of rows is incorrect.", 3, rows);
    }
}