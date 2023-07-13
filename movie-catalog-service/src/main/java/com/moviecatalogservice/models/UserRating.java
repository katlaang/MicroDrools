package com.moviecatalogservice.models;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRating {

    private String userId;
    private List<Rating> ratings;
}
