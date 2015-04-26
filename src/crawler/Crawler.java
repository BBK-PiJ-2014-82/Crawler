package crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class contains a 'main' method that will initiate and run the
 * web crawler. It will request a URL, maximum number of links to search and 
 * maximum depth of pages to search from the user.  It will then print all
 * resulting URL's found from the search.  Finally, it will request whether
 * the user would like to do a second search.
 * 
 * @author James Hill
 */
public class Crawler {
    
    static boolean again = false;
    static Connection conn;
    static LinkedList<String> list;
    static String initialURL;
    static WebCrawler crawl;
    
    // Strings for the interface.
    static String requestURL = "Please type in a URL you would like to crawl:";
    
    // Strings for the database.
    static String dbName = "testDB;";
    static String protocol = "jdbc:derby:memory:";
    
    /**
     * This is the main method from which the web crawler will be run. In this
     * case the 'args' parameter is ignored.
     * 
     * @param args ignored.
     */
    public static void main(String[] args) {
        
        // Setup the connection for an embedded database.
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(protocol + dbName + "create=true");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        do{
            // Request a URL from the user.
            System.out.println(requestURL);
            System.out.println();
            initialURL = "http://www.bbc.co.uk/news";
            
            // Start the crawler.
            crawl = new WebCrawlerImplNoSearch(100, 3);
            list = crawl.crawl(initialURL, conn);
            
            // Print the list of results.
            list.stream().forEach((link) -> {System.out.println("   "+link);});
            System.out.println();
            
            // Ask whether the user wishes to continue.
            System.out.println("Would you like to continue?");
        } while(again);
        
        // Close the connection to the database.
        try {conn.close();} catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
}