package crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
        InputStreamReader stream = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader buff = new BufferedReader(stream);
        try{
            char check;
            int next = buff.read();
            while(next != -1){
                check = Character.toLowerCase((char)next);
                if(check == Character.toLowerCase(ch1)){return true;}
                else if (check == Character.toLowerCase(ch2)){return false;}
                next = buff.read();
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