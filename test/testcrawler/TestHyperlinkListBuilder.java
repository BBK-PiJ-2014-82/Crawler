package testcrawler;

import crawler.HyperlinkListBuilder;
import crawler.HyperlinkListBuilderImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
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
    
    // A boolean to be used in the tests.
    boolean isEmpty = false;
    
    // The HyperlinkListBuilder 
    HyperlinkListBuilder build;
    
    // A list of InputStream's for testing this class.
    InputStream doubleStream;
    InputStream doubleRelativeStream;
    InputStream mixed2RelativeStream;
    InputStream mixed4RelativeStream;
    InputStream mixed4RelativeSpacedStream;
    InputStream mixed4RelativeUpperStream;
    InputStream singleStream;
    InputStream singleRelativeStream;
    InputStream zeroStream;
    
    // Integer used for holding count of URL's found.
    int listSize;
    
    // List<URL>s for checkingn the results from the build object.
    List<URL> checkList, testList;
    
    // Platform independent line separator.
    String sep = System.getProperty("line.separator");
    
    // URL strings.
    String base = "https://wikileaks.org/index.en.html/";
    String link1 = "https://wikileaks.org";
    String link2 = "http://www.google.com";
    String relative1 = "index";
    String relative2 = "About";
    
    // Strings for building the mock HTML code.
    String docType = "<!DOCTYPE html>" + sep;
    String closeBody = "</body>" + sep;
    String closeHead = "</head>" + sep;
    String closeHTML = "</html>" + sep;
    String hyperbase = "<base href=\""+base+"\">" + sep;
    String hyperbaseSpaced = "<  base href=\""+base+"\">" + sep;
    String hyperbaseUpper = "<BASE href=\""+base+"\">" + sep;
    String hyperlink1 = "<a href=\""+link1+"\">Courage is contagious</a>" + sep;
    String hyperlink1Spaced = "<  a  href=\""+link1+"\">Courage is contagious</a>" + sep;
    String hyperlink2 = "<a href=\""+link2+"\">Search!</a>" + sep;
    String hyperlink2Spaced = "< a href=\""+link2+"\">Search!</a>" + sep;
    String hyperlinkUpper1 = "<A href=\""+link1+"\">Courage is contagious</A>" + sep;
    String hyperlinkUpper2 = "<A href=\""+link2+"\">Search!</A>" + sep;
    String openBody = "<body>" + sep;
    String openBodySpaced = "< body >" + sep;
    String openHead = "<head>" + sep;
    String openHTML = "<html>" + sep;
    String relativeLink1 = "<a href=\""+relative1+"\">Index</a>" + sep;
    String relativeLink1Spaced = "< a  href=\""+relative1+"\">Index</a>" + sep;
    String relativeLink2 = "<a href=\""+relative2+"\">About</a>" + sep;
    String relativeLink2Spaced = "<  a  href=\""+relative2+"\">About</a>" + sep;
    
    // Strings for the URL's that will be created.
    String doubleString;
    String doubleRelativeString;
    String mixed2RelativeString;
    String mixed4RelativeString;
    String mixed4RelativeSpacedString;
    String mixed4RelativeUpperString;
    String singleString;
    String singleRelativeString;
    String zeroString;
    
    // A URL for testing the strings have been parsed correctly.
    URL testURL1, testURL2, testURL3, testURL4;
    
    @Before
    public void prepare(){
        build = new HyperlinkListBuilderImpl();
    }
    
    @Test
    public void checkCreateListReturnsEmptyListIfZeroHyperlinks(){
        zeroString = "";
        zeroStream = new ByteArrayInputStream(
                zeroString.getBytes(StandardCharsets.ISO_8859_1));
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
        singleStream = new ByteArrayInputStream(
                singleString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(singleStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 1, listSize);
        try{
            testURL1 = new URL(link1);
            assertTrue("URL not in list", testList.contains(testURL1));
            singleStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
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
        doubleStream = new ByteArrayInputStream(
                doubleString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(doubleStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 2, listSize);
        try{
            testURL1 = new URL(link1);
            testURL2 = new URL(link2);
            checkList = new LinkedList<>();
            checkList.add(testURL1);
            checkList.add(testURL2);
            assertTrue("URLs not in list", testList.containsAll(checkList));
            doubleStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void checkCreateListReturnsSingleRelativeHyperlink(){
        singleRelativeString =
                docType +
                openHead +
                hyperbase +
                closeHead +
                openHTML +
                openBody +
                relativeLink1 +
                closeBody +
                closeHTML;
        singleRelativeStream = new ByteArrayInputStream(
                singleRelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(singleRelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 1, listSize);
        try{
            testURL1 = new URL(hyperbase + relativeLink1);
            assertTrue("URL not in list", testList.contains(testURL1));
            singleRelativeStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void checkCreateListReturnsTwoRelativeHyperlinks(){
        doubleRelativeString =
                docType +
                openHead +
                hyperbase +
                closeHead +
                openHTML +
                openBody +
                relativeLink1 +
                relativeLink2 +
                closeBody +
                closeHTML;
        doubleRelativeStream = new ByteArrayInputStream(
                doubleRelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(doubleRelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 2, listSize);
        try{
            testURL1 = new URL(hyperbase + relativeLink1);
            testURL2 = new URL(hyperbase + relativeLink2);
            checkList = new LinkedList<>();
            checkList.add(testURL1);
            checkList.add(testURL2);
            assertTrue("URLs not in list", testList.containsAll(checkList));
            doubleRelativeStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void checkCreateListReturnsMixedTwoHyperlinks(){
        mixed2RelativeString =
                docType +
                openHead +
                hyperbase +
                closeHead +
                openHTML +
                openBody +
                relativeLink1 +
                hyperlink1 +
                closeBody +
                closeHTML;
        mixed2RelativeStream = new ByteArrayInputStream(
                mixed2RelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed2RelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 2, listSize);
        try{
            testURL1 = new URL(hyperbase + relativeLink1);
            testURL2 = new URL(hyperlink1);
            checkList = new LinkedList<>();
            checkList.add(testURL1);
            checkList.add(testURL2);
            assertTrue("URLs not in list", testList.containsAll(checkList));
            mixed2RelativeStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void checkCreateListReturnsMixedFourHyperlinks(){
        mixed4RelativeString =
                docType +
                openHead +
                hyperbase +
                closeHead +
                openHTML +
                openBody +
                relativeLink1 +
                hyperlink1 +
                relativeLink2 +
                hyperlink2 +
                closeBody +
                closeHTML;
        mixed4RelativeStream = new ByteArrayInputStream(
                mixed4RelativeString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed4RelativeStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 4, listSize);
        try{
            testURL1 = new URL(hyperbase + relativeLink1);
            testURL2 = new URL(hyperbase + relativeLink2);
            testURL3 = new URL(hyperlink1);
            testURL4 = new URL(hyperlink2);
            checkList = new LinkedList<>();
            checkList.add(testURL1);
            checkList.add(testURL2);
            checkList.add(testURL3);
            checkList.add(testURL4);
            assertTrue("URLs not in list", testList.containsAll(checkList));
            mixed4RelativeStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void checkCreateListReturnsMixedFourHyperlinksWithUpperCaseCommands(){
        mixed4RelativeUpperString =
                docType +
                openHead +
                hyperbaseUpper +
                closeHead +
                openHTML +
                openBody +
                relativeLink1 +
                hyperlinkUpper1 +
                relativeLink2 +
                hyperlinkUpper2 +
                closeBody +
                closeHTML;
        mixed4RelativeUpperStream = new ByteArrayInputStream(
                mixed4RelativeUpperString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed4RelativeUpperStream);
        listSize = testList.size();
        assertEquals("Uppercase commands not identified correctly.", 4, listSize);
        try{
            testURL1 = new URL(hyperbaseUpper + relativeLink1);
            testURL2 = new URL(hyperbaseUpper + relativeLink2);
            testURL3 = new URL(hyperlinkUpper1);
            testURL4 = new URL(hyperlinkUpper2);
            checkList = new LinkedList<>();
            checkList.add(testURL1);
            checkList.add(testURL2);
            checkList.add(testURL3);
            checkList.add(testURL4);
            assertTrue("URLs not in list", testList.containsAll(checkList));
            mixed4RelativeUpperStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
    
    @Test
    public void checkCreateListReturnsMixedFourHyperlinksWithExtraSpaces(){
        mixed4RelativeSpacedString =
                docType +
                openHead +
                hyperbaseSpaced +
                closeHead +
                openHTML +
                openBodySpaced +
                relativeLink1Spaced +
                hyperlink1Spaced +
                relativeLink2Spaced +
                hyperlink2Spaced +
                closeBody +
                closeHTML;
        mixed4RelativeSpacedStream = new ByteArrayInputStream(
                mixed4RelativeSpacedString.getBytes(StandardCharsets.ISO_8859_1));
        testList = build.createList(mixed4RelativeSpacedStream);
        listSize = testList.size();
        assertEquals("The List size is incorrect.", 4, listSize);
        try{
            testURL1 = new URL(hyperbase + relativeLink1);
            testURL2 = new URL(hyperbase + relativeLink2);
            testURL3 = new URL(hyperlink1);
            testURL4 = new URL(hyperlink2);
            checkList = new LinkedList<>();
            checkList.add(testURL1);
            checkList.add(testURL2);
            checkList.add(testURL3);
            checkList.add(testURL4);
            assertTrue("URLs not in list", testList.containsAll(checkList));
            mixed4RelativeSpacedStream.close();
        } catch (MalformedURLException exc) {
            System.err.println("Error processing stream: " + exc);
        } catch (IOException exc) {
            System.err.println("Error processing stream: " + exc);
        }
    }
}