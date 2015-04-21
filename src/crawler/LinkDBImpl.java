package crawler;

/**
 * This is an implementation of the LinkDB interface.
 * 
 * @author James Hill
 */
public class LinkDBImpl implements LinkDB {
    
    LinkDBImpl(){
        
    }
    
    @Override
    public boolean checkDuplicateResult(String hyperlink){
        return false;
    }
    
    @Override
    public boolean checkDuplicateTemp(String hyperlink){
        return false;
    }
    
    @Override
    public void linkVisited(String hyperlink){
        
    }
    
    @Override
    public void writeResult(String hyperlink){
        
    }
    
    @Override
    public void writeTemp(int priority, String hyperlink){
        
    }
}