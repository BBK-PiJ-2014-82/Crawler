package crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

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
    static String requestURL = "Type in a URL you would like to crawl from: ";
    static String requestContinue = "Continue with searching? 'Y' to continue: ";
    
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
        
        // Loop through the functions until the user chooses not to continue.
        do{
            int links = maxLinks();
            int depth = maxDepth();
            initialURL = requestURL();
            System.out.println();
            crawl = new WebCrawlerImplNoSearch(links, depth);
            list = crawl.crawl(initialURL, conn);
            printResults();
            System.out.println();
            again = userContinue();
        } while(again);
        
        // Close the connection to the database.
        try {conn.close();} catch (SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        
        // Show the user the program is complete.
        System.out.println();
        System.out.println("Crawler closing . . .");
    }
    
    /**
     * Requests a URL from the user, checks and returns it as a String.
     * 
     * @return a URL as entered by the user.
     */
    static private String requestURL(){
        System.out.print(requestURL);
        Scanner scan = new Scanner(System.in);
        String link = scan.nextLine();
        return link;
    }
    
    /**
     * Prints the list of results to the output for the user to see.
     */
    static private void printResults(){
        if(list == null){
            System.out.println("There are no results to display.");
        } else {
            System.out.println("This is a list of the results:");
            System.out.println();
            list.stream().forEach((link) -> {System.out.println("   " + link);});
        }
    }
    
    /**
     * Asks the user if they would like to continue with a new search.
     * 
     * @return the users response to the question.
     */
    static private boolean userContinue(){
        System.out.print(requestContinue);
        Scanner scan = new Scanner(System.in);
        String result = scan.nextLine();
        return result.length() == 1 && result.equalsIgnoreCase("y");
    }
    
    /**
     * This method asks whether the user would like to enter a maximum number
     * of links to search and returns a default otherwise.
     * 
     * @return the maximum number of links to search.
     */
    static private int maxLinks(){
        System.out.print("Set a maximum number of links to crawl? 'Y' to set: ");
        Scanner scan = new Scanner(System.in);
        String result = scan.nextLine();
        if(result.length() == 1 && result.equalsIgnoreCase("y")){
            System.out.print("Type your maximum number of links: ");
            if(scan.hasNextInt()){
                return scan.nextInt();
            }
            System.out.println("Invalid input: using default.");
        }
        return 100;
    }
    
    /**
     * This method asks whether the user would like to enter a maximum depth
     * of pages to search and returns a default otherwise.
     * 
     * @return the maximum depth of pages to search.
     */
    static private int maxDepth(){
        System.out.print("Set a maximum depth of pages to crawl?  'Y' to set: ");
        Scanner scan = new Scanner(System.in);
        String result = scan.nextLine();
        if(result.length() == 1 && result.equalsIgnoreCase("y")){
            System.out.print("Type your maximum depth of pages: ");
            if(scan.hasNextInt()){
                return scan.nextInt();
            }
            System.out.println("Invalid input: using default.");
        }
        return 2;
    }
}