package com.tencorners.movie.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieListDTO {

    List<MovieDTO> movies;

}
