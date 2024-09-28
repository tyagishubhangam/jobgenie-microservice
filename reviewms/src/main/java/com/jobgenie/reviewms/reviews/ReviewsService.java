package com.jobgenie.reviewms.reviews;

import java.util.List;


public interface ReviewsService {
    public List<Reviews> getAllReviews(Long companyId);
    public boolean addReview(Long companyId, Reviews reviews);
    public Reviews getReview( Long reviewId);
    public boolean updateReview(Long reviewId, Reviews reviews);
    public boolean deleteReview( Long reviewId);

}
