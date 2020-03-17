package com.tencorners.movie.services;

import com.tencorners.movie.domains.Movie;
import com.tencorners.movie.mapper.MovieMapper;
import com.tencorners.movie.models.MovieDTO;
import com.tencorners.movie.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    MovieService movieService;

    @Mock
    MovieRepository movieRepository;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        movieService = new MovieServiceImpl(MovieMapper.INSTANCE, movieRepository);

    }

    @Test
    public void getAllMoviesTest() throws Exception {

        // given
        List<Movie> movies = Arrays.asList(new Movie(), new Movie(), new Movie(), new Movie(), new Movie());

        // when
        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> movieDTOS = movieService.getAllMovies();

        // then
        assertEquals(5, movieDTOS.size());

    }
    
}
