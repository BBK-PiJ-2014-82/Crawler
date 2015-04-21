package crawler;

import java.net.URL;
import java.sql.Connection;

/**
 * This extension of the abstract class WebCrawlerImpl provides a 'search()'
 * method that ignores the parameters and always returns a 'true' value. The
 * initial version of the WebCrawler application does not search the URL's
 * gathered by the 'crawl()' method but simply records them all into the results
 * table. This extension allows the search element to be essentially
 * ignored.
 * 
 * @author James Hill
 */
public class WebCrawlerImplNoSearch extends WebCrawlerImpl {
    
    /**
     * The basic constructor for this class.
     * 
     * @param URL the initial URL that the webcrawler will start from.
     * @param conn the database connection the webcrawler will write/read to.
     */
    public WebCrawlerImplNoSearch(String URL, Connection conn) {
        super(URL, conn);
    }
    
    /**
     * This is the secondary constructor for this class that can also take in
     * arguments for the maximum number of links to be searched or the
     * maximum depth of the pages that will be searched.
     * 
     * @param URL the initial URL that the webcrawler will start from.
     * @param conn the database connection the webcrawler will write/read to.
     * @param maxLinks the maximum number of links to be processed.
     * @param maxDepth the maximum depth of web pages to be processed.
     */
    public WebCrawlerImplNoSearch(String URL, Connection conn, Integer maxLinks, Integer maxDepth) {
        super(URL, conn, maxLinks, maxDepth);
    }
    
    @Override
    public boolean search(URL currentURL, String...searchTerms){
        return true;
    }
}