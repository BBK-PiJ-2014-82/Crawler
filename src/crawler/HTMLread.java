package crawler;

import java.io.InputStream;

/**
 * This interface defines methods for parsing HTML commands from a stream
 * and constructing strings from the information that's read.
 *
 * @author James Hill
 */
public interface HTMLread {
    
    /**
     * This method consumes characters from the InputStream and stops when
     * either a char identical to 'ch1' or 'ch2' is encountered, ignoring case.
     * If neither character is encountered a false should be returned.
     * 
     * @param in an input stream from a HTML file.
     * @param ch1 the character being searched for.
     * @param ch2 the character representing the end of a search attempt.
     * @return the value of the statement 'ch1 is in this InputStream'.
     */
    boolean readUntil(InputStream in, char ch1, char ch2);
    
    /**
     * This method consumes up to and including the 1st non-whitespace
     * char from the InputStream or up to and including 'ch'. If the string is
     * entirely whitespace the method will return a null.
     * 
     * @param in an input stream from a HTML file.
     * @param ch a char that ends the method.
     * @return if 'ch' found, returns char null, else 1st non-whitespace char.
     */
    char skipSpace(InputStream in, char ch);
    
    /**
     * This method consumes characters from the InputStream and stops when
     * either a char identical to 'ch1' or 'ch2 is encountered, not ignoring
     * case. It then returns either a string including 'ch1' if 'ch1' is
     * encountered, or a String 'null' if 'ch2' is encountered. If neither char
     * is found then the method will return a null.
     * 
     * @param in an input stream from a HTML file.
     * @param ch1 the character being searched for.
     * @param ch2 the character representing the end of a search attempt.
     * @return the String searched for including 'ch1' or a String 'null'.
     */
    String readString(InputStream in, char ch1, char ch2);
}