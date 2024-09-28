package com.jobgenie.jobms.jobs.repositories;


import com.jobgenie.jobms.jobs.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Jobs, Long> {
}
