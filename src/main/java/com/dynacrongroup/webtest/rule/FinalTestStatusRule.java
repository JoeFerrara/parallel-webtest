package com.dynacrongroup.webtest.rule;

import com.dynacrongroup.webtest.SauceLabsCredentials;
import com.dynacrongroup.webtest.sauce.SauceREST;
import org.junit.runner.Description;

/**
 * Rule interacts with the SauceREST interface to report job pass/fail results.
 *
 * User: yurodivuie
 * Date: 3/11/12
 * Time: 9:47 AM
 */
public class FinalTestStatusRule extends ClassFinishRule {

    Boolean allTestsPassed = true;
    SauceREST sauceREST = new SauceREST(SauceLabsCredentials.getUser(), SauceLabsCredentials.getKey());

    private final String jobId;

    public FinalTestStatusRule(String jobId) {
        this.jobId = jobId;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        super.failed(e, description);
        allTestsPassed = false;
    }

    @Override
    protected void classFinished(Description description) {
        sendFinalTestStatusForJobId(jobId);
    }

    private void sendFinalTestStatusForJobId(String jobId) {
        if (allTestsPassed) {
            sauceREST.jobPassed(jobId);
        }
        else {
            sauceREST.jobFailed(jobId);
        }
    }
}