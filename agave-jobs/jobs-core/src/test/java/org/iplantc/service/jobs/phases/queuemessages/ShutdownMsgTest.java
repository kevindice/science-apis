package org.iplantc.service.jobs.phases.queuemessages;

import org.iplantc.service.jobs.exceptions.JobException;
import org.iplantc.service.jobs.phases.utils.TopicMessageSender;
import org.testng.annotations.Test;

/** This test suite requires the jobs service to be running. 
 * 
 * @author rcardone
 */
public class ShutdownMsgTest {
    /* ********************************************************************** */
    /*                              Test Methods                              */
    /* ********************************************************************** */    
    /* ---------------------------------------------------------------------- */
    /* shutdownAllPhases:                                                     */
    /* ---------------------------------------------------------------------- */
    @Test(enabled=true)
    public void shutdownAllPhases() throws JobException
    {   
        // This message targets all phases since we don't specifically name any.
        ShutdownMessage shutdownMsg = new ShutdownMessage();
        TopicMessageSender.sendShutdownMessage(shutdownMsg);
    }
}