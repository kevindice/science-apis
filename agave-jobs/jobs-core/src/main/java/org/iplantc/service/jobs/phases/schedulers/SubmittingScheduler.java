package org.iplantc.service.jobs.phases.schedulers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iplantc.service.jobs.dao.JobDao;
import org.iplantc.service.jobs.exceptions.JobException;
import org.iplantc.service.jobs.exceptions.JobSchedulerException;
import org.iplantc.service.jobs.model.Job;
import org.iplantc.service.jobs.model.enumerations.JobPhaseType;
import org.iplantc.service.jobs.model.enumerations.JobStatusType;
import org.iplantc.service.jobs.phases.schedulers.Strategies.impl.JobCreateOrder;
import org.iplantc.service.jobs.phases.schedulers.Strategies.impl.TenantRandom;
import org.iplantc.service.jobs.phases.schedulers.Strategies.impl.UserRandom;

/** Concrete job phase scheduler.
 * 
 * @author rcardone
 */
public final class SubmittingScheduler 
 extends AbstractPhaseScheduler
{
    /* ********************************************************************** */
    /*                               Constants                                */
    /* ********************************************************************** */
    // Tracing.
    private static final Logger _log = Logger.getLogger(SubmittingScheduler.class);

    // A place to squirrel away our triggers.
    private List<JobStatusType> _phaseTriggerStatuses;
    
    /* ********************************************************************** */
    /*                              Constructors                              */
    /* ********************************************************************** */
    /* ---------------------------------------------------------------------- */
    /* constructor:                                                           */
    /* ---------------------------------------------------------------------- */
    public SubmittingScheduler() throws JobException
    {
        super(JobPhaseType.SUBMITTING,
              new TenantRandom(),
              new UserRandom(),
              new JobCreateOrder());
    }

    /* ********************************************************************** */
    /*                            Protected Methods                           */
    /* ********************************************************************** */
    /* ---------------------------------------------------------------------- */
    /* getPhaseTriggerStatus:                                                 */
    /* ---------------------------------------------------------------------- */
    @Override
    protected List<JobStatusType> getPhaseTriggerStatuses()
    {
        if (_phaseTriggerStatuses == null)
        {
            _phaseTriggerStatuses = new ArrayList<>(3);
            _phaseTriggerStatuses.add(JobStatusType.SUBMITTING);
            _phaseTriggerStatuses.add(JobStatusType.STAGED);
            _phaseTriggerStatuses.add(JobStatusType.STAGING_JOB);
        }
        return _phaseTriggerStatuses;
    }
    
    /* ---------------------------------------------------------------------- */
    /* getPhaseCandidateJobs:                                                 */
    /* ---------------------------------------------------------------------- */
    @Override
    protected List<Job> getPhaseCandidateJobs(List<JobStatusType> statuses) 
      throws JobSchedulerException
    {
        // Initialize result list.
        List<Job> jobs = null;
        
        // Query all jobs that are ready for this state.
        try {jobs = JobDao.getByStatus(statuses);}
            catch (Exception e)
            {
                String msg = _phaseType.name() + " scheduler unable to retrieve jobs.";
                _log.error(msg, e);
                throw new JobSchedulerException(msg, e);
            }
        
        return jobs;
    }
}
