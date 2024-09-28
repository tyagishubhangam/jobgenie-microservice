package com.jobgenie.jobms.jobs;

import com.jobgenie.jobms.jobs.dto.JobDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobsController {
    private JobsService jobsService;

    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> findJobs() {

        return ResponseEntity.ok(jobsService.getAllJobs());
    }
    @PostMapping
    public ResponseEntity<String> addJob(@RequestBody Jobs job) {
        jobsService.createJob(job);
        return new ResponseEntity<>("Job Created Successfully", HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobDto> findJob(@PathVariable Long id) {
        JobDto jobDto = jobsService.findJobById(id);
        if (jobDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jobsService.findJobById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean deleted = jobsService.deleteJobById(id);
        if (deleted) {
            return new ResponseEntity<>("Job Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Job Not Found", HttpStatus.NOT_FOUND);

    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Jobs job) {
        boolean updated = jobsService.updateJob(id, job);
        if (updated) {
            return new ResponseEntity<>("Job Updated Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Job Not Found", HttpStatus.NOT_FOUND);
    }
}
