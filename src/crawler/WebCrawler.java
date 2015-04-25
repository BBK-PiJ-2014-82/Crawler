package crawler;

import java.net.URL;
import java.sql.Connection;
import java.util.LinkedList;

/**
 * The web crawler is a class that constructs a database of URL links.  Having
 * been provided a link it will perform a 'breadth-first' search of the links
 * that it can follow from the 1st web page using a database to store
 * a record of it's progress. It then stores results that meet criteria defined
 * in implementations of the interface in a LinkedList that is returned.
 * 
 * @author James Hill
 */
public interface WebCrawler {
        
    /**
     * This crawl method opens a HTTP connection to the starting URL and reviews
     * the entire web page, saving links into a temporary table to record the
     * progress of the crawler through it's crawl. A results table is also
     * created so that URL's which meet a criteria to be specified by the
     * programmer in implementations of the interface can be stored for later
     * review by the program user.
     * 
     * @return a LinkedList of all links found by the WebCrawler.
     * @param startURL the URL from which the WebCrawler will start crawling.
     * @param conn the connection that will be used to build the database.
     */
    LinkedList<String> crawl(URL startURL, Connection conn);
}