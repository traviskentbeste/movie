package com.tencorners.movie.models;

import lombok.Data;

import java.util.Date;

@Data
public class TheMovieDbDTO {

    private Long id;
    private String overview;
    private String originalLanguage;
    private String originalTitle;
    private Boolean video;
    private String title;
    private String posterPath;
    private String backdropPath;
    private Date releaseDate;
    private Float popularity;
    private Float voteAverage;
    private Boolean adult;
    private Float voteCount;
    private String createdDatetimestamp;
    private String updatedDatetimestamp;

}
