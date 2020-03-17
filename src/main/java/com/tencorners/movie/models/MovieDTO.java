package com.tencorners.movie.models;

import lombok.Data;

@Data
public class MovieDTO {

    private Long id;
    private String filename;
    private Long theMovieDbId;
    private String createdDatetimestamp;
    private String updatedDatetimestamp;

}
