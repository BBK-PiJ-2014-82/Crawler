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
        int next;
        char check;
        while(true){
            try{
                next = in.read();
                if(next != -1){
                    return false;
                } else {
                    check = (char)(byte)next;
                    if(check == ch1){
                        return true;
                    } else if (check == ch2){
                        return false;
                    }
                }
            } catch(IOException exception){
                System.err.println("Error processing stream: " + exception);
            }
        }
    }
    
    @Override
    public char skipSpace(InputStream in, char ch){return 'a';}
    
    @Override
    public String readString(InputStream in, char ch1, char ch2){return null;}
}