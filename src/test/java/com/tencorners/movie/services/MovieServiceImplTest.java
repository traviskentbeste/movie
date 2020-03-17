package com.tencorners.movie.services;

import com.tencorners.movie.domains.Movie;
import com.tencorners.movie.mapper.MovieMapper;
import com.tencorners.movie.models.MovieDTO;
import com.tencorners.movie.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    public static final Long ID = 1L;
    public static final String FILENAME = "/Volumes/movies";
    public static final String CREATED_DATETIMESTAMP = "2020-01-01 00:00:00";
    public static final String UPDATED_DATETIMESTAMP = "2020-01-02 00:00:00";

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Mock
    MovieRepository movieRepository;

    MovieMapper movieMapper = MovieMapper.INSTANCE;

    MovieService movieService;

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        movieService = new MovieServiceImpl(movieMapper, movieRepository);

    }

    @Test
    public void testGetAllMovies() throws Exception {

        // given
        Movie movie1 = new Movie();
        movie1.setId(ID);
        movie1.setFilename(FILENAME);
        movie1.setCreatedDatetimestamp(formatter.parse(CREATED_DATETIMESTAMP));
        movie1.setUpdatedDatetimestamp(formatter.parse(UPDATED_DATETIMESTAMP));

        Movie movie2 = new Movie();
        movie2.setId(ID);
        movie2.setFilename(FILENAME);
        movie2.setCreatedDatetimestamp(formatter.parse(CREATED_DATETIMESTAMP));
        movie2.setUpdatedDatetimestamp(formatter.parse(UPDATED_DATETIMESTAMP));

        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        // when
        List<MovieDTO> movieDTOS = movieService.getAllMovies();

        // then
        assertEquals(2, movieDTOS.size());

    }

    @Test
    public void getMovieById() throws Exception {

        //given
        Movie movie = new Movie();
        movie.setId(ID);
        movie.setFilename(FILENAME);
        movie.setCreatedDatetimestamp(formatter.parse(CREATED_DATETIMESTAMP));
        movie.setUpdatedDatetimestamp(formatter.parse(UPDATED_DATETIMESTAMP));

        when(movieRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(movie));

        //when
        MovieDTO movieDTO = movieService.getMovieById(ID);
        assertEquals(Long.valueOf(ID), movieDTO.getId());
        assertEquals(FILENAME, movieDTO.getFilename());
        assertEquals(CREATED_DATETIMESTAMP, movieDTO.getCreatedDatetimestamp());
        assertEquals(UPDATED_DATETIMESTAMP, movieDTO.getUpdatedDatetimestamp());

    }

    @Test
    public void createNewMovie() throws Exception {

        //given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        Movie savedMovie = new Movie();
        savedMovie.setId(movieDTO.getId());
        savedMovie.setFilename(movieDTO.getFilename());
        savedMovie.setCreatedDatetimestamp(formatter.parse(movieDTO.getCreatedDatetimestamp()));
        savedMovie.setUpdatedDatetimestamp(formatter.parse(movieDTO.getUpdatedDatetimestamp()));

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        //when
        MovieDTO savedDTO = movieService.createMovie(movieDTO);

        //then
        assertEquals(Long.valueOf(ID), movieDTO.getId());
        assertEquals(movieDTO.getFilename(), savedDTO.getFilename());
        assertEquals(movieDTO.getCreatedDatetimestamp(), savedDTO.getCreatedDatetimestamp());
        assertEquals(movieDTO.getUpdatedDatetimestamp(), savedDTO.getUpdatedDatetimestamp());

    }

    @Test
    public void saveMovieByDTO() throws Exception {

        //given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        Movie savedMovie = new Movie();
        savedMovie.setId(movieDTO.getId());
        savedMovie.setFilename(movieDTO.getFilename());
        savedMovie.setCreatedDatetimestamp(formatter.parse(movieDTO.getCreatedDatetimestamp()));
        savedMovie.setUpdatedDatetimestamp(formatter.parse(movieDTO.getUpdatedDatetimestamp()));

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        //when
        MovieDTO savedDTO = movieService.saveMovieByDTO(1L, movieDTO);

        //then
        assertEquals(Long.valueOf(ID), movieDTO.getId());
        assertEquals(movieDTO.getFilename(), savedDTO.getFilename());
        assertEquals(movieDTO.getCreatedDatetimestamp(), savedDTO.getCreatedDatetimestamp());
        assertEquals(movieDTO.getUpdatedDatetimestamp(), savedDTO.getUpdatedDatetimestamp());

    }

    @Test
    public void deleteMovieById() throws Exception {

        Long id = ID;

        movieService.deleteMovieById(id);

        verify(movieRepository, times(1)).deleteById(anyLong());

    }
    
}
