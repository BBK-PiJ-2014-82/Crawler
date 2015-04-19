package crawler;

import java.io.InputStream;
import java.io.IOException;

/**
 * This is an implementation of the HTMLread class that is used for parsing
 * web pages. This only checks for characters coded in the HTML character set
 * which is based upon [ISO-8859-1].
 * 
 * @author James Hill
 */
public class HTMLreadImpl implements HTMLread {
    
    /**
     * This is the constructor for HTMLread objects.
     */
    public void HTMLreadImpl(){}
    
    @Override
    public boolean readUntil(InputStream in, char ch1, char ch2){
        try{
            char check;
            int next = in.read();
            while(next != -1){
                check = Character.toLowerCase((char)next);
                if(check == Character.toLowerCase(ch1)){return true;}
                else if (check == Character.toLowerCase(ch2)){return false;}
                next = in.read();
            }
        } catch(IOException exc){
            System.err.println("Error processing stream: " + exc);
        }
        return false;
    }
    
    @Override
    public char skipSpace(InputStream in, char ch){
        try{
            char check;
            int next = in.read();
            while(next != -1){
                check = (char)next;
                if(check == ch){return '\0';}
                else if (!Character.isWhitespace(check)){return check;}
                next = in.read();
            }
        } catch(IOException exc){
            System.err.println("Error processing stream: " + exc);
        }
        return '\0';
    }
    
    @Override
    public String readString(InputStream in, char ch1, char ch2){
        try{
            String foundString = "";
            char check;
            int next = in.read();
            while(next != -1){
                check = (char)next;
                foundString = foundString + check;
                if(check == ch1){return foundString;}
                else if (check == ch2){return null;}
                next = in.read();
            }
        } catch(IOException exc){
            System.err.println("Error processing stream: " + exc);
        }
        return null;
    }
}