package crawler;

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
     * URL provided and return it as a List object. If the URL does not point
     * to a HTML file then the method throws an IllegalArgumentException. It is
     * the responsibility of the programmer using this class to check the URL
     * before passing it to this method.
     * 
     * @return the list of hyperlinks stored as strings.
     * @param webpage this is the HTML document that will be scanned for links.
     */
    List<String> createList(URL webpage) throws IllegalArgumentException;
}