package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int failCount = 0;
    private static final int limit = 2;

    public boolean retry(ITestResult result) {
        if (failCount < limit) {
            failCount++;
            return true;
        }
        return false;
    }
}
