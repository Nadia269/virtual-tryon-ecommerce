package com.shop.shop.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shop.shop.Holder.TokenHolder;
import com.shop.shop.Model.ProductModel;
import com.shop.shop.Model.Review;
import com.shop.shop.Model.UserModel;
import com.shop.shop.Repository.ProductRepo;
import com.shop.shop.Repository.ReviewRepository;
import com.shop.shop.Repository.UserRepository;
import com.shop.shop.config.JwtService;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private UserRepository userRepository;

    // public Review createReview(Long userId, Long productId, int rating) {
    //     ProductModel product = productRepository.findById(productId)
    //             .orElseThrow(() -> new RuntimeException("Product not found"));

    //     UserModel user = userRepository.findById(userId)
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     Review review = new Review();
    //     review.setUser(user);
    //     review.setProduct(product);
    //     review.setRating(rating);

    //     return reviewRepository.save(review);
    // }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

   public Review saveReview(Long productId, int rating) {

    String email = JwtService.extractUsername(TokenHolder.getToken());
    if (email == null) {
        throw new RuntimeException("Invalid or missing JWT token.");
    }


    UserModel currentUser = userRepository.findByEmail(email);
    if (currentUser == null) {
        throw new RuntimeException("User not found for email: " + email);
    }

    ProductModel product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

 
    Review review = new Review();
    review.setUser(currentUser);
    review.setProduct(product);
    review.setRating(rating);
    review.setCreatedAt(LocalDateTime.now());

    // ✅ Save and return
    return reviewRepository.save(review);
}


}
