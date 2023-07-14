package com.example.ratingsservice.service;

import com.example.ratingsservice.config.DroolsRuleConfig;
import com.example.ratingsservice.models.Rating;
import com.example.ratingsservice.models.User;
import com.example.ratingsservice.models.User.Status;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class RatingsService {

    private final KieSession kieSession = new DroolsRuleConfig().getRatingKieSession();

    public User getUserRatings(String userId) {
        User user = new User(userId);
        try {
            for(Rating rating: user.getRatings()) {
                kieSession.insert(rating);
                kieSession.fireAllRules();
            }
        } finally {
            kieSession.dispose();
        }
        return user;
    }

    public Object createUserRating(String userId, String movieId, int rating) {
        User user = new User(userId);
        Rating newRating = new Rating(movieId, rating);
        boolean newMovie = user.addRating(newRating);
        try {
            kieSession.insert(user);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        //Don't allow suspected bots to add ratings
        if(user.getStatus() != Status.BOT) {
            user.saveToFile();
            return newMovie;
        }
        return null;
    }

}
