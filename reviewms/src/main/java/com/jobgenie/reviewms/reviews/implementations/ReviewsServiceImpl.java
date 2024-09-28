package com.jobgenie.reviewms.reviews.implementations;


import com.jobgenie.reviewms.reviews.Reviews;
import com.jobgenie.reviewms.reviews.ReviewsService;
import com.jobgenie.reviewms.reviews.repositories.ReviewsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepo reviewsRepo;



    public ReviewsServiceImpl(ReviewsRepo reviewsRepo) {
        this.reviewsRepo = reviewsRepo;

    }

    @Override
    public List<Reviews> getAllReviews(Long companyId) {
        return reviewsRepo.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Reviews reviews) {

        if (companyId != null && reviews != null) {
            reviews.setCompanyId(companyId);
            reviewsRepo.save(reviews);
            return true;
        }
        return false;
    }

    @Override
    public Reviews getReview( Long reviewId) {
        return reviewsRepo.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReview( Long reviewId, Reviews updatedReview) {
       Reviews reviews = reviewsRepo.findById(reviewId).orElse(null);
        if (reviews != null) {

            reviews.setCompanyId(updatedReview.getCompanyId());
            reviews.setTitle(updatedReview.getTitle());
            reviews.setDescription(updatedReview.getDescription());
            reviews.setRating(updatedReview.getRating());
            reviewsRepo.save(reviews);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview( Long reviewId) {
        Reviews review = reviewsRepo.findById(reviewId).orElse(null);

        if (review != null) {
            reviewsRepo.delete(review);
            return true;
        }
        return false;
    }
}
