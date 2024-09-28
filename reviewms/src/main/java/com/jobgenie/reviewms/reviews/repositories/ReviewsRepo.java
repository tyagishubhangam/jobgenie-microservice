package com.jobgenie.reviewms.reviews.repositories;


import com.jobgenie.reviewms.reviews.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepo extends JpaRepository<Reviews,Long> {
    public List<Reviews> findByCompanyId(Long companyId);
}
