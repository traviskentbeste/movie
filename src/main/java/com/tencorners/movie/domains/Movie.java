package com.tencorners.movie.domains;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Movie extends BaseEntity {

    private String filename;
    private Long theMovieDbId;

}
