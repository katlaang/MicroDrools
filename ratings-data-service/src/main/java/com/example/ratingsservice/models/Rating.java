package com.example.ratingsservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
public class Rating {

    private int minRating = 1;
    private int maxRating = 5;

    private String movieId;
    private int rating;
    private String ratingDescription;

    public Rating(String movieId, int rating) {
        this.movieId = movieId;
        this.rating = rating < minRating ? minRating : Math.min(rating, maxRating);
    }
}
