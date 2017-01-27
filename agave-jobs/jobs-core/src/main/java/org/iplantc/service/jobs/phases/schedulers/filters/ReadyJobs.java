package org.iplantc.service.jobs.phases.schedulers.filters;

import java.util.List;

import org.iplantc.service.jobs.model.Job;

/** Nothing more than a container for the results of 
 * AbstractPhaseScheduler.getJobsReadyForPhase().
 * 
 * @author rcardone
 */
public class ReadyJobs
{
    // -- Fields
    private List<Job>              jobs;      // never null
    private IPostPriorityJobFilter jobFilter; // can be null
    
    // -- Constructors
    public ReadyJobs(List<Job> jobs){this(jobs, null);}
    public ReadyJobs(List<Job> jobs, IPostPriorityJobFilter filter)
    {
        this.jobs = jobs;
        this.jobFilter = filter;
    }
    
    // -- Acessors
    public List<Job> getJobs()
    {
        return jobs;
    }
    public void setJobs(List<Job> jobs)
    {
        this.jobs = jobs;
    }
    public IPostPriorityJobFilter getJobFilter()
    {
        return jobFilter;
    }
    public void setJobFilter(IPostPriorityJobFilter jobFilter)
    {
        this.jobFilter = jobFilter;
    }
}
