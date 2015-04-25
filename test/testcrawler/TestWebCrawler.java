package testcrawler;

import crawler.WebCrawler;
import crawler.WebCrawlerImplNoSearch;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a testing class for the HyperlinkListBuilder class in 'Crawler'.
 * 
 * @author James Hill
 */
public class TestWebCrawler {
    
    Connection conn;
    
    LinkedList<String> crawlList;
    
    String testSite = "http://testingsite8.webnode.com/";
    
    // A list of expected hyperlinks.
    String link1 = "http://us.webnode.com/personal-websites/";
    
    // A list of relative hyperlinks.
    String rel1 = "/testpage1/";
    String rel2 = "/testpage2/";
    String rel3 = "/testpage3/";
    
    // Strings for the database.
    static String dbName = "testDB;";
    static String protocol = "jdbc:derby:";
    
    URL testURL;
    
    WebCrawler crawlie;
    
    @Before
    public void setup(){
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(protocol + dbName + "create=true");
            testURL = new URL(testSite);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | SQLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void testCrawlerReturns1stPageReferences(){
        // Setup the webcrawler.
        crawlie = new WebCrawlerImplNoSearch();
        crawlList = crawlie.crawl(testURL, conn);
        
        boolean contains;
        contains = crawlList.contains(link1);
        assertTrue("The link is not in the list.", contains);
    }
    
    @Test
    public void testCrawlerReturnsIgnoresUnhelpfulValues(){
        // Setup the webcrawler.
        crawlie = new WebCrawlerImplNoSearch(0, 0);
        crawlList = crawlie.crawl(testURL, conn);
        
        boolean contains;
        contains = crawlList.contains(link1);
        assertTrue("The link is not in the list.", contains);
    }
    
    @Test
    public void testCrawlerReturns1stPageReferencesWithMaxLinks(){
        // Setup the webcrawler.
        crawlie = new WebCrawlerImplNoSearch(4, 1000);
        crawlList = crawlie.crawl(testURL, conn);
        
        // Test the size of the list is correct.
        int size = crawlList.size();
        assertEquals("The length is not correct.", 4, size);
    }
    
    @Test
    public void testCrawlerReturns1stPageReferencesWithMaxDepth(){
        // Setup the webcrawler.
        crawlie = new WebCrawlerImplNoSearch(1000, 1);
        crawlList = crawlie.crawl(testURL, conn);
        
        // Test the size of the list is correct.
        int size = crawlList.size();
        assertEquals("The length is not correct.", 4, size);
    }
}