package crawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
    final private char sep = System.getProperty("line.separator").charAt(0);
    
    /**
     * This object provides methods for parsing the HTML document streamed.
     */
    private final HTMLread reader;
    
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
     * This is the basic class constructor. It creates a new HTMLread object
     * that can be used across by all methods to parse strings from InputStream.
     */
    public HyperlinkListBuilderImpl(){
        reader = new HTMLreadImpl();
    }
    
    @Override
    public List<URL> createList(InputStream in) {
        linkList = new LinkedList<>();
        String tag;
        try {
            do{
                if(reader.readUntil(in, '<', sep)){
                    tag = reader.readString(in, '>', sep);
                    if(tag != null){
                        tag = tag.toLowerCase();
                        if(tag.length() > 3){
                            if(tag.substring(0, 2).contentEquals("a ") ||
                                tag.substring(0, 4).contentEquals("base") ||
                                tag.substring(0, 4).contentEquals("body")){
                                    String command = extractCommand(tag);
                                    enactCommand(tag, command);
                            }
                        }
                    }
                }
            } while(in.available() != 0);
            in.close();
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        return linkList;
    }
    
    /**
     * This private method extracts the command from a string. Returns a null
     * if a relevant command cannot be extracted.
     */
    private String extractCommand(String command){
        if(command.substring(0, 2).contentEquals("a ")){
            return "a";
        } else if (command.substring(0, 4).contentEquals("base")){
            return "base";
        } else if (command.substring(0, 4).contentEquals("body")){
            return "body";
        }
        return null;
    }
    
    /**
     * This private method checks the command string that has been identified
     * from the input stream and acts upon the command that has been found.
     */
    private void enactCommand(String tag, String command){
        String URLtext;
        URL tempURL;
        try{
            switch(command){
                case "a":       URLtext = extractHTML(tag);
                                if(!URLtext.isEmpty()){
                                    if(!checkRelative(URLtext)){
                                        tempURL = new URL(URLtext);
                                        linkList.add(tempURL);
                                    } else if (baseURL != null && bodyReached){
                                        tempURL = new URL(baseURL, URLtext);
                                        linkList.add(tempURL);
                                    }
                                }
                                break;
                case "base":    if(!bodyReached){
                                    URLtext = extractHTML(tag);
                                    if(!URLtext.isEmpty()){
                                        baseURL = new URL(URLtext);
                                    }
                                }
                                break;
                case "body":    bodyReached = true;
                default:        break;
            }
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    /**
     * This private method captures text that appears to be a hyperlink from
     * an InputStream. If no valid hyperlink text is found the method returns
     * a null.
     * 
     * @param in an InputStream where a hyperlink is expected.
     * @return a valid hyperlink text or a null. 
     */
    private String extractHTML(String tag){
        String URLtext = null;
        if(tag.contains("href=")){
            int index = tag.indexOf("href=");
            int quotesIndex = tag.indexOf('\"', index);
            URLtext = tag.substring(quotesIndex+1, tag.indexOf('\"', quotesIndex+1));
        }
        return URLtext;
    }
    
    /**
     * This method checks if the string represents either a relative or absolute
     * URL.
     */
    private boolean checkRelative(String URLtext){
        String testHTTP;
        testHTTP = URLtext.substring(0, 4).toLowerCase();
        return !testHTTP.contains("http");
    }
}