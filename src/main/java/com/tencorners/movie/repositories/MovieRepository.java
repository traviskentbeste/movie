package com.tencorners.movie.repositories;

import com.tencorners.movie.domains.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie getMovieByFilename(String filename);
    List<Movie> getMovieByTheMovieDbIdIsNull();
}
