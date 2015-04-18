package crawler;

import java.io.InputStream;
import java.io.IOException;

/**
 * This is an implementation of the HTMLread class that is used for parsing
 * web pages.
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
        byte b;
        char check;
        try{
            int next = in.read();
            while(next != -1){
                b = (byte)next;
                check = Character.toLowerCase((char)b);
                if(check == Character.toLowerCase(ch1)){return true;}
                else if (check == Character.toLowerCase(ch2)){return false;}
                next = in.read();
            }
        } catch(IOException exception){
            System.err.println("Error processing stream: " + exception);
        }
        return false;
    }
    
    @Override
    public char skipSpace(InputStream in, char ch){return 'a';}
    
    @Override
    public String readString(InputStream in, char ch1, char ch2){return null;}
}