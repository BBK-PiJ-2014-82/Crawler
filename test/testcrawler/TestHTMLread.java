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
    char ch1, ch2, ch3;
    
    // A string of characters for testing that the HTMLread methods can parse
    // correctly.
    String chString = "abcdefghijklmnopqrstuvwxyz 0123456789<>";
    String whtString = "\t\n\u000B\f\r\u001C\u001D\u001E\u001F aZ0";
    String allwhtString = "\t\n\u000B\f\r\u001C\u001D\u001E\u001F ";
    String testString = "abcdefghijklmnopqrstuvwxy";
    String blankString = null;
    
    // A null String for testing empty input streams.
    String nullString = null;
    
    // InputStreams that can be passed strings to be parsed in the tests.
    InputStream chStream;
    InputStream whtStream;
    InputStream allwhtStream;
    
    // A HTMLreading object for parsing the strings.
    HTMLread reader;
    
    @Before
    public void prepare(){
        // Convert the string into a list of 
        chStream = new ByteArrayInputStream(chString.getBytes(StandardCharsets.ISO_8859_1));
        whtStream = new ByteArrayInputStream(whtString.getBytes(StandardCharsets.ISO_8859_1));
        allwhtStream = new ByteArrayInputStream(allwhtString.getBytes(StandardCharsets.ISO_8859_1));
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
        assertTrue("Character not found.", exists);
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
    
    @Test
    public void checkSkipSpaceReturnsNull(){
        ch1 = '\f';
        ch2 = reader.skipSpace(whtStream, ch1);
        assertEquals("Null character was not returned.", '\0', ch2);
    }
    
    @Test
    public void checkSkipSpaceReturnsNullIfSearchedCharIsFirstNonWhiteSpace(){
        ch1 = 'a';
        ch2 = reader.skipSpace(whtStream, ch1);
        assertEquals("Null character was not returned.", '\0', ch2);
    }
    
    @Test
    public void checkSkipSpaceReturnsFirstNonWhiteSpaceChar(){
        ch1 = '0';
        ch2 = reader.skipSpace(whtStream, ch1);
        assertEquals("Incorrect character was returned.", 'a', ch2);
    }
    
    @Test
    public void checkSkipSpaceReturnsNullWithAllWhiteSpace(){
        ch1 = '0';
        ch2 = reader.skipSpace(allwhtStream, ch1);
        assertEquals("Incorrect character was returned.", '\0', ch2);
    }
    
    @Test
    public void checkReadStringReturnsCorrectString(){
        ch1 = 'z';
        ch2 = '9';
        blankString = reader.readString(chStream, ch1, ch2);
        assertEquals("Incorrect string was returned.", testString, blankString);
    }
    
    @Test
    public void checkReadStringReturnsSingleCharacterString(){
        ch1 = 'a';
        ch2 = '9';
        blankString = reader.readString(chStream, ch1, ch2);
        assertEquals("Incorrect string was returned.", "a", blankString);
    }
    
    @Test
    public void checkReadStringReturnsNullFromChar2(){
        ch1 = '9';
        ch2 = 'z';
        blankString = reader.readString(chStream, ch1, ch2);
        assertEquals("Null was not returned.", null, blankString);
    }
    
    @Test
    public void checkReadStringReturnsNullIfNeitherCharFound(){
        ch1 = '+';
        ch2 = '-';
        blankString = reader.readString(chStream, ch1, ch2);
        assertEquals("Null was not returned.", null, blankString);
    }
}