package com.tencorners.movie.services;

import com.tencorners.movie.domains.TheMovieDb;
import com.tencorners.movie.models.MovieDTO;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();
    MovieDTO getMovieById(Long id);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO saveMovieByDTO(Long id, MovieDTO movieDTO);
    MovieDTO patchMovie(Long id, MovieDTO movieDTO);
    void deleteMovieById(Long id);
    MovieDTO getMovieByFilename(String filename);
    TheMovieDb jsonObjectToTheMovieDbEntity(JSONObject jsonObject) throws ParseException;
    List<MovieDTO> getAllNotFoundMovies();
    
}
