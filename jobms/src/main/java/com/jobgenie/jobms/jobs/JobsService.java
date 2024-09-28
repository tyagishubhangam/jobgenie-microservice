package com.jobgenie.jobms.jobs;

import com.jobgenie.jobms.jobs.dto.JobDto;

import java.util.List;

public interface JobsService {
    public List<JobDto> getAllJobs();
    public void createJob(Jobs job);
    public JobDto findJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Jobs job);
}
