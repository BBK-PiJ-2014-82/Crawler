package testcrawler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
            TestHTMLread.class,
            TestHyperlinkListBuilder.class,
        })

public class SuiteCrawler {}