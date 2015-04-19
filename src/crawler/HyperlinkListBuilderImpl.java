package crawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * This is an implementation of the HyperlinkListBuilder interface.
 * 
 * @author James Hill
 */
public class HyperlinkListBuilderImpl implements HyperlinkListBuilder {
    
    /**
     * This notes whether the <body> command has been reached in the HTML doc
     * while it is being scanned.
     */
    private boolean bodyReached = false;
    
    // Platform independent line separator.
    private char sep = System.getProperty("line.separator").charAt(0);
    
    /**
     * This object provides methods for parsing the HTML document streamed.
     */
    private HTMLread reader;
    
    /**
     * This is the List in which the hyperlinks will be stored.
     */
    private List<URL> linkList;
    
    /**
     * This baseURL is created if a HTML <base> command is found during the
     * scan of the provided URL. This is then used to construct any relative
     * URL's found in the rest of the document.
     */
    private URL baseURL;
    
    /**
     * This is the basic class constructor.
     */
    public void HyperlinkListBuilderImpl(){}
    
    @Override
    public List<URL> createList(InputStream in) {
        linkList = new LinkedList<URL>();
        reader = new HTMLreadImpl();
        char cmd;
        String command;
        String URLtext;
        URL tempURL;
        try {
            do{
                if(reader.readUntil(in, '<', sep)){
                    cmd = reader.skipSpace(in, sep);
                    if(cmd == 'a' || cmd == 'b'){
                        command = reader.readString(in, ' ', '>');
                        switch(command){
                            case "a":       URLtext = extractHTML(in);
                                            tempURL = new URL(URLtext);
                                            linkList.add(tempURL);
                                            break;
                            case "base":    URLtext = extractHTML(in);
                                            if(!bodyReached){
                                                baseURL = new URL(URLtext);
                                                linkList.add(baseURL);
                                            }
                                            break;
                            case "body":    bodyReached = true;
                            default:        break;
                        }
                    }
                }
            } while(in.read() != -1);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        return linkList;
    }
}