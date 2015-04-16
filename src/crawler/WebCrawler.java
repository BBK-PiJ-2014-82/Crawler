package crawler;

import java.io.File;
import java.net.URL;

/**
 * The web crawler is a class that constructs a database of URL links.  Having
 * been provided a link it will perform a 'breadth-first' search of the links
 * that it can follow from the 1st web page.  It provides the possibility for
 * a complex 'search()' method to be called by the crawler method which can be
 * implemented in detail in future.
 * 
 * @author James Hill
 */
public interface WebCrawler {
        
    /**
     * This crawl method opens a HTTP connection to the starting URL and reviews
     * the entire web page, saving links into a temporary table &
     * allowing for a future 'search()' method that would scan a web page &
     * add the page to a results table if the result was true. In the
     * absence of a completed 'search()' method the crawler will always store
     * the scanned links into the results table.
     * 
     * @param startURL the URL from which the WebCrawler will start crawling.
     * @param temp the file holding temporary links for crawling.
     * @param results the file holding links identified through 'search()'.
     */
    void crawl(URL startURL, File temp, File results);
    
    /**
     * This default method has been added for possible future implementation.
     * The most recent URL crawled can be passed to the method along with a
     * list of strings which the user would like to search the URL for. If any
     * of the strings are found a 'true' value should be returned; otherwise a
     * 'false' value should be returned. As this method is not required to be
     * implemented for the current project it is now providing a default value
     * of true.
     * 
     * @param currentURL the URL most recently process for links.
     * @param searchTerms terms being searched for in provided URL.
     * @return 
     */
    default boolean search(URL currentURL, String...searchTerms){
        return true;
    }
}