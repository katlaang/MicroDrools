package com.moviecatalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {
    private String name;
    private String description;
    private int rating;
    private String ratingDescription;
}
