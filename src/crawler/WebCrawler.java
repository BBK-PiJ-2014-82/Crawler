package crawler;

import java.io.File;
import java.net.URL;

/**
 * The web crawler is a class that constructs a database of URL links.  Having
 * been provided a link it will perform a 'breadth-first' search of the links
 * that it can follow from the 1st web page using a temporary file to store
 * a record of it's progress. It then stores results that meet criteria defined
 * in implementations of the interface in a results file.
 * 
 * @author James Hill
 */
public interface WebCrawler {
        
    /**
     * This crawl method opens a HTTP connection to the starting URL and reviews
     * the entire web page, saving links into a temporary table to record the
     * progress of the crawler through it's crawl. A results table is also
     * provided so that URL's which meet a criteria to be specified by the
     * programmer in implementations of the interface can be stored for later
     * review by the program user.
     * 
     * @return the results file constructed by the crawler.
     * @param startURL the URL from which the WebCrawler will start crawling.
     * @param temp the file holding temporary links for crawling.
     * @param results the file holding links that meet specified criteria.
     */
    File crawl(URL startURL, File temp, File results);
}