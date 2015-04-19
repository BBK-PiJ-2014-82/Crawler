package testcrawler;

import crawler.HyperlinkListBuilder;
import crawler.HyperlinkListBuilderImpl;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a testing class for the HyperlinkListBuilder class in 'Crawler'.
 * 
 * @author James Hill
 */
public class TestHyperlinkListBuilder {
    
    // A boolean to be used in the tests;
    boolean isEmpty = false;
    
    // The HyperlinkListBuilder 
    HyperlinkListBuilder build;
    
    // Integer used for holding count of URL's found.
    int listSize;
    
    // List<URL>s for checkingn the results from the build object.
    List<URL> testList;
    
    // Strings for building the mock HTML code.
    String docType = "<!DOCTYPE html>";
    String closeBody = "</body>";
    String closeHTML = "</html>";
    String hyperlink1 = "<a href=\"https://wikileaks.org/index.en.html\">";
    String hyperlink2 = "<a href=\"https://www.torproject.org/\">";
    String openBody = "<body>";
    String openHTML = "<html>";
    
    
    // Strings for the URL's that will be created.
    String doubleString;
    String doubleRelativeString;
    String mixed2RelativeString;
    String mixed4RelativeString;
    String mixed4RelativeUpperString;
    String singleString;
    String singleRelativeString;
    String zeroString;
    
    // A list of InputStream's for testing this class.
    InputStream doubleStream;
    InputStream doubleRelativeStream;
    InputStream mixed2RelativeStream;
    InputStream mixed4RelativeStream;
    InputStream mixed4RelativeUpperStream;
    InputStream singleStream;
    InputStream singleRelativeStream;
    InputStream zeroStream;
    
    @Before
    public void prepare(){
        build = new HyperlinkListBuilderImpl();
    }
    
    @Test
    public void checkCreateListReturnsEmptyListIfZeroHyperlinks(){
        zeroString = "";
        zeroStream = new ByteArrayInputStream(zeroString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(zeroStream);
        isEmpty = testList.isEmpty();
        assertTrue("The returned List is not empty.", isEmpty);
    }
    
    @Test
    public void checkCreateListReturnsSingleHyperlink(){
        singleString =
                docType +
                openHTML +
                openBody +
                hyperlink1 +
                closeBody +
                closeHTML;
        singleStream = new ByteArrayInputStream(singleString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(singleStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 1, listSize);
    }
    
    @Test
    public void checkCreateListReturnsTwoHyperlinks(){
        doubleString =
                docType +
                openHTML +
                openBody +
                hyperlink1 +
                hyperlink2 +
                closeBody +
                closeHTML;
        doubleStream = new ByteArrayInputStream(doubleString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(doubleStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 2, listSize);
    }
    
    @Test
    public void checkCreateListReturnsSingleRelativeHyperlink(){
        singleRelativeStream = new ByteArrayInputStream(singleRelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(singleRelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 1, listSize);
    }
    
    @Test
    public void checkCreateListReturnsTwoRelativeHyperlinks(){
        doubleRelativeStream = new ByteArrayInputStream(doubleRelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(doubleRelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 2, listSize);
    }
    
    @Test
    public void checkCreateListReturnsMixedTwoHyperlinks(){
        mixed2RelativeStream = new ByteArrayInputStream(mixed2RelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed2RelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 2, listSize);
    }
    
    @Test
    public void checkCreateListReturnsMixedFourHyperlinks(){
        mixed4RelativeStream = new ByteArrayInputStream(mixed4RelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed4RelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 4, listSize);
    }
    
    @Test
    public void checkCreateListReturnsMixedFourHyperlinksWithUpperCaseCommands(){
        mixed4RelativeUpperStream = new ByteArrayInputStream(mixed4RelativeUpperString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed4RelativeUpperStream);
        listSize = testList.size();
        assertEquals("Uppercase commands not identified correctly.", 4, listSize);
    }
}