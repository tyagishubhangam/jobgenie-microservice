package com.jobgenie.companyms.companies.repositories;

import com.jobgenie.companyms.companies.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Companies, Long> {
}
