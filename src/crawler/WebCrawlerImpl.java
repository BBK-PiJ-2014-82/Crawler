package crawler;

import java.net.URL;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * This is an abstract implementation of the WebCrawler class.  It provides a
 * completed and final implementation of the required 'crawl()' method.  It
 * declares the 'search()' method to be abstract. A concrete extension of this
 * class is therefore required to fully implement the WebCrawler interface.
 * This is to allow for different implementations of the 'search()' method that
 * can still be consistently called by the 'crawl()' method.
 * 
 * @author James Hill
 */
public abstract class WebCrawlerImpl implements WebCrawler {
    
    /**
     * The maximum number of links that will be searched by the 'crawl' method.
     */
    private int maxLinks = 100;
    
    /**
     * The maximum depth of links that will be searched by the 'crawl' method.
     * The depth is the number of links away from the original start URL that
     * the crawler will search.
     */
    private int maxDepth = 2;
    
    /**
     * This integer records the number of links that have been processed by the
     * web crawler. It is used to compare against the maxLinks integer.
     */
    private int linksProcessed = 0;
    
    /**
     * This priority queue number is assigned to links on the temporary table.
     * The links will be given a priority number that reflects the depth of the
     * web page away from the initial URL provided to the class.
     */
    private int priority = 0;
    
    /**
     * This List will hold links scanned from a web page which can be iterated
     * through to print out to the database.
     */
    private List<String> links;
    
    /**
     * This is the basic constructor without parameters for the WebCrawler.
     * This is used when the programmer wishes to use the default values for
     * the maxLinks and maxDepth variables.
     */
    public WebCrawlerImpl(){}
    
    /**
     * This constructor allows changes to the values of the maxLinks variable
     * and/or the maxDepth variable.
     * 
     * @param maxLinks the maximum number of links to be processed.
     * @param maxDepth the maximum depth of web pages to be processed.
     */
    public WebCrawlerImpl(Integer maxLinks, Integer maxDepth){
        this.maxLinks = maxLinks != null ? maxLinks : this.maxLinks;
        this.maxDepth = maxDepth != null ? maxDepth : this.maxDepth;
    }
    
    @Override
    final public LinkedList<String> crawl(URL startURL, Connection conn){
        LinkedList<String> results;
        return results;
    }
    
    /**
     * This method has been designated abstract to allow different future
     * implementations. The most recent URL crawled can be passed to the method
     * with a list of strings the user would like to search the URL for. If any
     * strings are found a 'true' value should be returned; otherwise a
     * 'false' value should be returned.
     * 
     * @return the existence of the provided search terms in the URL.
     * @param currentURL the URL most recently process for links.
     * @param searchTerms terms being searched for in provided URL.
     */
    abstract public boolean search(URL currentURL, String...searchTerms);
}