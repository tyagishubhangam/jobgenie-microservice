package com.jobgenie.companyms.companies.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Review-Microservice", url = "${review-microservice.url}")
public interface ReviewClient {
    @GetMapping("/reviews/averageRating")
    public double getAverageRating(@RequestParam("companyId") Long companyId);
}
