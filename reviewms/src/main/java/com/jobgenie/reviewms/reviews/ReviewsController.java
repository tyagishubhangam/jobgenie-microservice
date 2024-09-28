package com.jobgenie.reviewms.reviews;

import com.jobgenie.reviewms.reviews.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
    ReviewsService reviewsService;
    ReviewMessageProducer reviewMessageProducer;

    public ReviewsController(ReviewsService reviewsService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewsService = reviewsService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Reviews>> getReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewsService.getAllReviews(companyId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addReviews(@RequestBody Reviews reviews, @RequestParam Long companyId) {
       boolean isReviewAdded = reviewsService.addReview(companyId, reviews);
       if (isReviewAdded) {
           reviewMessageProducer.sendMessage(reviews);
           return new ResponseEntity<>("Review added Successfully", HttpStatus.OK);
       }
       return new ResponseEntity<>("Review not added", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> getReview(@PathVariable Long reviewId) {
        Reviews review = reviewsService.getReview(reviewId);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Reviews reviews) {
        boolean isReviewUpdated = reviewsService.updateReview( reviewId, reviews);
        if (isReviewUpdated) {
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review not updated", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewsService.deleteReview( reviewId);
        if (isReviewDeleted) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public double getAverageRating(@RequestParam Long companyId) {
        List<Reviews> reviews = reviewsService.getAllReviews(companyId);
        return reviews.stream().mapToDouble(Reviews::getRating).average().orElse(0.0);
    }

}
