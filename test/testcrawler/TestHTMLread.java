package testcrawler;

import crawler.HTMLread;
import crawler.HTMLreadImpl;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * This is a testing class for the HTMLread class in 'Crawler'.
 * 
 * @author James Hill
 */
public class TestHTMLread {
    
    // A boolean to be used in tests.
    boolean exists = false;
    
    // Characters to be used in tests.
    char ch1, ch2;
    
    // A string of characters for testing that the HTMLread methods can parse
    // correctly.
    String chString = "abcdefghijklmnopqrstuvwxyz 0123456789<>";
    
    // A null String for testing empty input streams.
    String nullString = null;
    
    // InputStreams that can be passed strings to be parsed in the tests.
    InputStream chStream;
    
    // A HTMLreading object for parsing the strings.
    HTMLread reader;
    
    @Before
    public void prepare(){
        // Convert the string into a list of 
        chStream = new ByteArrayInputStream(chString.getBytes(StandardCharsets.ISO_8859_1));
        reader = new HTMLreadImpl();
    }
    
    @After
    public void close(){
        // Close off the streams before ending the testing.
        try{chStream.close();}
        catch(IOException exception){
            System.err.println("Error processing stream: " + exception);
        }
    }
    
    @Test
    public void checkReadUntilStopsWhenFirstCharacterEncountered(){
        ch1 = 'z';
        ch2 = '0';
        exists = reader.readUntil(chStream, ch1, ch2);
        assertTrue("Character not found.", exists);
    }
    
    @Test
    public void checkReadUntilStopsWhenSecondCharacterEncountered(){
        ch1 = '0';
        ch2 = 'z';
        exists = reader.readUntil(chStream, ch1, ch2);
        assertFalse("Character not found.", exists);
    }
    
    @Test
    public void checkReadUntilStopsWhenFirstCharacterDifferentCaseEncountered(){
        ch1 = 'Z';
        ch2 = '0';
        exists = reader.readUntil(chStream, ch1, ch2);
        assertFalse("Character not found.", exists);
    }
    
    @Test
    public void checkReadUntilStopsWhenSecondCharacterDifferentCaseEncountered(){
        ch1 = '0';
        ch2 = 'Z';
        exists = reader.readUntil(chStream, ch1, ch2);
        assertFalse("Character not found.", exists);
    }
    
    @Test
    public void checkReadUntilReturnsFalseWhenStreamEnds(){
        ch1 = '-';
        ch2 = '+';
        exists = reader.readUntil(chStream, ch1, ch2);
        assertFalse("Incorrect value returned when stream ends.", exists);
    }
}