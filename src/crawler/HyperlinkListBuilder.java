package crawler;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * This class accepts a URL to a HTML document and processes it to create and
 * return a list of hyperlinks stored as strings. The URL should point to a
 * HTML document and throw an exception if this is not the case.
 * 
 * @author James Hill
 */
public interface HyperlinkListBuilder {
    
    /**
     * This will create a list of hyperlinks found within the HTML file at the
     * URL provided and return it as a List object.
     * 
     * @return the list of hyperlinks stored as URLs.
     * @param in this is the HTML document streamed for parsing.
     */
    List<URL> createList(InputStream in);
}