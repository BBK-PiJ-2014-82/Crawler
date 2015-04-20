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
        char cmd;
        String command;
        try {
            do{
                if(reader.readUntil(in, '<', sep)){
                    cmd = Character.toLowerCase(reader.skipSpace(in, sep));
                    if(cmd == 'a' || cmd == 'b'){
                        command = reader.readString(in, ' ', '>');
                        if(command != null){
                            enactCommand(command, in);
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
     * This private method checks the command string that has been identified
     * from the input stream and acts upon the command that has been found.
     */
    private void enactCommand(String command, InputStream in){
        String URLtext;
        URL tempURL;
        try{
            switch(command.toLowerCase()){
                case "":        URLtext = extractHTML(in);
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
                case "ase":     if(!bodyReached){
                                    URLtext = extractHTML(in);
                                    if(!URLtext.isEmpty()){
                                        baseURL = new URL(URLtext);
                                    }
                                }
                                break;
                case "ody":     bodyReached = true;
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
    private String extractHTML(InputStream in){
        String URLtext = null;
        if(reader.readUntil(in, '"', '>')){
            URLtext = reader.readString(in, '"', '>');
        }
        return URLtext;
    }
    
    /**
     * This method checks if the string represents either a relative or absolute
     * URL.
     */
    private boolean checkRelative(String URLtext){
        String testHTTP;
        testHTTP = URLtext.substring(0, 3).toLowerCase();
        return testHTTP.contains("http");
    }
}