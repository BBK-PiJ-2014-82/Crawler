package crawler;

/**
 * This is an interface defining a class for a database that can write
 * hyperlinks and their priority numbers to a 'temporary' table, modify the
 * priority numbers, extract the hyperlinks from the table, and write hyperlinks
 * to a second 'results' table.
 *
 * @author James Hill
 */
public interface LinkDB {
    
    /**
     * This method checks whether the hyperlink already exists within the
     * 'results' table.
     * 
     * @param hyperlink the hyperlink to be checked for duplication.
     * @return 'true' if the hyperlink already exists in the table.
     */
    boolean checkExistsResult(String hyperlink);
    
    /**
     * This method checks whether the hyperlink already exists within the
     * 'temporary' table.
     * 
     * @param hyperlink the hyperlink to be checked for duplication.
     * @return 'true' if the hyperlink already exists in the table.
     */
    boolean checkExistsTemp(String hyperlink);
    
    /**
     * This method will change the priority number of the link passed as a
     * parameter to '0' to show that it has already been visited.
     * 
     * @param hyperlink the hyperlink that's been visited.
     */
    void linkVisited(String hyperlink);
    
    /**
     * This method writes a hyperlink into the 'results' table.
     * 
     * @param hyperlink the address of the web page.
     */
    void writeResult(String hyperlink);
    
    /**
     * This method writes a new hyperlink and the associated priority queue
     * number for that link into the 'temporary' table.
     * 
     * @param priority the depth of web page the hyperlink was found on.
     * @param hyperlink the address of the web page.
     */
    void writeTemp(int priority, String hyperlink);
}