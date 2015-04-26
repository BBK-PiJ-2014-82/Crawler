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
    public List<URL> createList(String base, InputStream in) {
        linkList = new LinkedList<>();
        try {
            baseURL = new URL(base);
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        }
        String tag;
        try {
            do{
                if(reader.readUntil(in, '<', sep)){
                    char c = Character.toLowerCase(reader.skipSpace(in, sep));
                    if(c != '\0' && (c == 'a' || c == 'b')){
                        tag = c + reader.readString(in, ' ', '>');
                        String command = extractCommand(tag);
                        if(command != null){
                            enactCommand(in, command);
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
    private String extractCommand(String tag){
        tag = tag.toLowerCase();
        if(tag.length() == 1){if(tag.contentEquals("a")){return "a";}}
        else if(tag.length() == 4){if(tag.contentEquals("base")){return "base";}}
        return null;
    }
    
    /**
     * This private method checks the command string that has been identified
     * from the input stream and acts upon the command that has been found.
     */
    private void enactCommand(InputStream in, String command){
        boolean ifJavaScript = false;
        String URLtext;
        URL tempURL;
        try{
            switch(command){
                case "a":       URLtext = extractHTML(in);
                                if(!URLtext.isEmpty()){
                                    if(URLtext.length() > 9){
                                        if(URLtext.substring(0, 10).equalsIgnoreCase("javascript")){
                                            ifJavaScript = true;
                                        }
                                    }
                                    if(!ifJavaScript){
                                        if(!checkRelative(URLtext)){
                                            tempURL = new URL(URLtext);
                                            linkList.add(tempURL);
                                        } else {
                                            tempURL = new URL(baseURL, URLtext);
                                            linkList.add(tempURL);
                                        }
                                    }
                                }
                                break;
                case "base":    URLtext = extractHTML(in);
                                if(!URLtext.isEmpty()){
                                    if(URLtext.length() > 9){
                                        if(URLtext.substring(0, 10).equalsIgnoreCase("javascript")){
                                            ifJavaScript = true;
                                        }
                                    }
                                    if(!ifJavaScript){
                                        baseURL = new URL(URLtext);
                                    }
                                }
                default:        break;
            }
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream line 113: " + exc);
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
        char tempChar;
        String URLtext = "";
        do{
            tempChar = reader.skipSpace(in, sep);
            if(tempChar == 'h' || tempChar == 'H'){
                URLtext = reader.readString(in, '\"', ' ');
                if(URLtext != null){
                    if(URLtext.equalsIgnoreCase("ref=")){
                        URLtext = reader.readString(in, '\"', sep);
                        return URLtext;
                    }
                }
            }
        } while(tempChar != '\0');
        return URLtext;
    }
    
    /**
     * This method checks if the string represents either a relative or absolute
     * URL.
     */
    private boolean checkRelative(String URLtext){
        if(URLtext.length() > 3){
            String testHTTP;
            testHTTP = URLtext.substring(0, 4).toLowerCase();
            return !testHTTP.contains("http");
        }
        return true;
    }
}