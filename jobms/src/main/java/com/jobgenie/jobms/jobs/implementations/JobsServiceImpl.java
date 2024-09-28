package com.jobgenie.jobms.jobs.implementations;


import com.jobgenie.jobms.jobs.clients.CompanyClient;
import com.jobgenie.jobms.jobs.clients.ReviewClient;
import com.jobgenie.jobms.jobs.dto.JobDto;
import com.jobgenie.jobms.jobs.external.Company;
import com.jobgenie.jobms.jobs.Jobs;
import com.jobgenie.jobms.jobs.JobsService;
import com.jobgenie.jobms.jobs.external.Review;
import com.jobgenie.jobms.jobs.mapper.JobDtoMapper;
import com.jobgenie.jobms.jobs.repositories.JobRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobsServiceImpl implements JobsService {
    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    JobRepo jobRepository;
    int attempts = 0;
    @Autowired
    RestTemplate restTemplate;

    public JobsServiceImpl(JobRepo jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    private Long newId = 0L;



    @Override
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback"
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDto> getAllJobs() {
        System.out.println("Attempt=" + ++attempts);
        List<JobDto> jobDtos = new ArrayList<>();
        List<Jobs> jobs = jobRepository.findAll();


        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<String> companyBreakerFallback(Exception e) {
        List<String> list = new ArrayList<>();
        list.add("DUMMY--DUMMY");
        return list;
    }
    public JobDto convertToDto(Jobs job) {
        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobDtoMapper.mapToJobDto(job, company, reviews);
    }

    @Override
    public void createJob(Jobs job) {
//        job.setId(++newId);
        jobRepository.save(job);

    }

    @Override
    public boolean updateJob(Long id, Jobs updatedJob) {
        Optional<Jobs> jobOpt =  jobRepository.findById(id);

            if(jobOpt.isPresent()){
                Jobs job = jobOpt.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());

                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);
                return true;
            }

        return false;
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            if(jobRepository.existsById(id)){
       jobRepository.deleteById(id);
        return true;}
        return false;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public JobDto findJobById(Long id) {
        Jobs job =jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }
}
