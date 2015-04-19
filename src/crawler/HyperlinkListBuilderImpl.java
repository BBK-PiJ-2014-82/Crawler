package crawler;

import java.net.URL;
import java.util.List;

/**
 * This is an implementation of the HyperlinkListBuilder interface.
 * 
 * @author James Hill
 */
public class HyperlinkListBuilderImpl {
    
    /**
     * This notes whether the <body> command has been reached in the HTML doc
     * while it is being scanned.
     */
    private boolean bodyReached = false;
    
    /**
     * This is the List in which the hyperlinks will be stored.
     */
    private List<String> linkList;
    
    /**
     * This is the basic class constructor.
     */
    HyperlinkListBuilderImpl(){}
    
    @Override
    List<String> createList(URL webpage) throws IllegalArgumentException {
        return linkList;
    }
}