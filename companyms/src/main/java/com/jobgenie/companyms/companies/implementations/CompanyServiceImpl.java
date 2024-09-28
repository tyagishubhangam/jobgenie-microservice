package com.jobgenie.companyms.companies.implementations;

import com.jobgenie.companyms.companies.Companies;
import com.jobgenie.companyms.companies.CompanyService;
import com.jobgenie.companyms.companies.clients.ReviewClient;
import com.jobgenie.companyms.companies.dto.ReviewMessage;
import com.jobgenie.companyms.companies.repositories.CompanyRepo;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    CompanyRepo companyRepo;
    ReviewClient reviewClient;


    public CompanyServiceImpl(CompanyRepo companyRepo, ReviewClient reviewClient) {
        this.companyRepo = companyRepo;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Companies> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public void addCompany(Companies company) {
        companyRepo.save(company);
    }

    @Override
    public Companies getCompanyById(Long id) {
       Optional<Companies> company = companyRepo.findById(id);
        return company.orElse(null);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try{
            if(companyRepo.existsById(id)){
            companyRepo.deleteById(id);
            return true;}return false;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateCompany(Long id, Companies updatedCompany) {
        Optional<Companies> companyOpt = companyRepo.findById(id);
        if (companyOpt.isPresent()) {
            Companies company = companyOpt.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());

            companyRepo.save(company);
            return true;
        }
        return false;

    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Companies company = companyRepo.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("Company not found"));
        double averageRating = reviewClient.getAverageRating(reviewMessage.getCompanyId());
        company.setAverageRating(averageRating);
        companyRepo.save(company);
    }
}
