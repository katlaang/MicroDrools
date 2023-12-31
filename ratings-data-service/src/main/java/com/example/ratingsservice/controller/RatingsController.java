package com.example.ratingsservice.controller;

import com.example.ratingsservice.models.User;
import com.example.ratingsservice.service.RatingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class  RatingsController {

    @RequestMapping("/{userId}")
    public User getRatingsOfUser(@PathVariable String userId) {

        return new RatingsService().getUserRatings(userId);
    }

    @RequestMapping("/addUserRating/{userId}/{movieId}/{rating}")
    public ResponseEntity<String> addUserRating(@PathVariable String userId, @PathVariable String movieId, @PathVariable int rating) {

        Object newMovie = new RatingsService().createUserRating(userId, movieId, rating);
        if(newMovie != null) {
            boolean newMovieBoolean = (boolean) newMovie;
            if (newMovieBoolean) {
                return ResponseEntity.ok().body("Rating added succesfully");
            } else {
                return ResponseEntity.ok().body("Rating updated succesfully");
            }
        }
        else {
            return ResponseEntity.badRequest().body("User suspected of being a BOT. Rating was not added");
        }
    }
}
