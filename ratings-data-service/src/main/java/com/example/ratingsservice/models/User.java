package com.example.ratingsservice.models;

import java.io.FileReader;
import java.util.*;
import java.io.*;

public class User {

    private String userId;

    private Status status;

    private List<Rating> ratings;

    private HashMap<String, List<Rating>> file;

    public enum Status {
        NEW_USER,
        HEAVY_USER,
        BOT
    }

    public User(String userId) {
        this.userId = userId;
        this.ratings = new ArrayList<Rating>();
        readFromFile(this.userId);
    }

    public String getUserId() {
        return this.userId;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public int getRatingCount() {
        return this.ratings.size();
    }

    public int getAverageRating() {
        return this.ratings.stream().mapToInt(Rating::getRating).sum() / this.getRatingCount();
    }

    public boolean addRating(Rating rating) {
        boolean newMovie = true;
        for (Rating ratingItem : this.ratings) {
            if (Objects.equals(ratingItem.getMovieId(), rating.getMovieId())) {
                ratingItem.setRating(rating.getRating());
                newMovie = false;
            }
        }
        if (newMovie) {
            this.ratings.add(rating);
        }
        return newMovie;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void readFromFile(String userId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ratings-data-service/src/main/resources/data/userRatings.csv"))) {
            String line;
            file = new HashMap<>();
            //Read lines until at the end of file
            while ((line = reader.readLine()) != null) {
                //Format is userId, movieId1, rating1, movieId2, rating2,...
                String[] userData = line.split(",");
                if (!userData[0].isEmpty()) {
                    List<Rating> ratings = new ArrayList<Rating>();
                    for (int i = 1; i < userData.length; i += 2) {
                        String movieId = userData[i];
                        int ratingValue = Integer.parseInt(userData[i + 1]);
                        Rating rating = new Rating(movieId, ratingValue);
                        if(this.getUserId().equals(userData[0])) {
                            this.addRating(rating);
                        }
                        ratings.add(rating);
                    }
                    //Add user and ratings into the HashMap
                    file.put(userData[0], ratings);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Returns boolean on wether a new movie was added to the file
    public void saveToFile(Rating newRating) {
        try (FileWriter writer = new FileWriter("ratings-data-service/src/main/resources/data/userRatings.csv", false)) {
            StringBuilder userData = new StringBuilder();
            file.put(this.userId, this.ratings);
            //Add ratings
            Set<String> userIds = this.file.keySet();
            for (String userId : userIds) {
                userData.append(userId);
                List<Rating> ratingsForUser = this.file.get(userId);
                for (Rating rating : ratingsForUser) {
                    userData.append(",").append(rating.getMovieId()).append(",").append(rating.getRating());
                }
                userData.append("\n");
            }
            userData.append(System.lineSeparator());
            writer.write(userData.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
