package com.jobgenie.jobms.jobs.mapper;

import com.jobgenie.jobms.jobs.Jobs;
import com.jobgenie.jobms.jobs.dto.JobDto;
import com.jobgenie.jobms.jobs.external.Company;
import com.jobgenie.jobms.jobs.external.Review;

import java.util.List;

public class JobDtoMapper {
    public static JobDto mapToJobDto(Jobs job, Company company, List<Review> reviews){
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setDescription(job.getDescription());
        jobDto.setTitle(job.getTitle());
        jobDto.setLocation(job.getLocation());
        jobDto.setMaxSalary(job.getMaxSalary());
        jobDto.setMinSalary(job.getMinSalary());
        jobDto.setCompany(company);
        jobDto.setReviews(reviews);
        return jobDto;
    }
}
