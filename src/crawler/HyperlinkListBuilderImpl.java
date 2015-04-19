package crawler;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * This is an implementation of the HyperlinkListBuilder interface.
 * 
 * @author James Hill
 */
public class HyperlinkListBuilderImpl implements HyperlinkListBuilder {
    
    /**
     * This baseURL is created if a HTML <base> command is found during the
     * scan of the provided URL. This is then used to construct any relative
     * URL's found in the rest of the document.
     */
    private URL baseURL;
    
    /**
     * This notes whether the <body> command has been reached in the HTML doc
     * while it is being scanned.
     */
    private boolean bodyReached = false;
    
    /**
     * This is the List in which the hyperlinks will be stored.
     */
    private List<URL> linkList;
    
    /**
     * This is the basic class constructor.
     */
    public void HyperlinkListBuilderImpl(){}
    
    @Override
    public List<URL> createList(InputStream in) {
        return linkList;
    }
}