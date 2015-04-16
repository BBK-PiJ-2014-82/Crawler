package crawler;

import java.net.URL;

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
    
    @Override
    public boolean search(URL currentURL, String...searchTerms){
        return true;
    }
}