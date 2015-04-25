package testcrawler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is a testing suite that runs all previously created test classes for
 * this project.
 * 
 * @author James Hill
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
            TestHTMLread.class,
            TestHyperlinkListBuilder.class,
            TestLinkDB.class
        })

public class SuiteCrawler {}