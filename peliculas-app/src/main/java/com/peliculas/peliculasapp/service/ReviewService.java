package com.peliculas.peliculasapp.service;

import com.peliculas.peliculasapp.dto.ReviewDTO;
import com.peliculas.peliculasapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public List<ReviewDTO> getReviewsByContentId(Integer contentId) {
        return reviewRepository.getReviewsByContentId(contentId);
    }
}
