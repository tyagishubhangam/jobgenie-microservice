package com.jobgenie.jobms.jobs.clients;

import com.jobgenie.jobms.jobs.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-microservice", url = "${company-microservice.url}")
public interface CompanyClient {
    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable Long id);
}
