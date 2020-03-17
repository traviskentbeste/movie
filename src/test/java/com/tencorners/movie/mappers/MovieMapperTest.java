package com.tencorners.movie.mappers;

import com.tencorners.movie.domains.Movie;
import com.tencorners.movie.mapper.MovieMapper;
import com.tencorners.movie.models.MovieDTO;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieMapperTest {

    public static final Long ID = 1L;
    public static final String FILENAME = "/Volumes/movies";
    public static final String CREATED_DATETIMESTAMP = "2020-01-01 00:00:00";
    public static final String UPDATED_DATETIMESTAMP = "2020-01-02 00:00:00";

    MovieMapper movieMapper = MovieMapper.INSTANCE;

    @Test
    public void accountToAccountDTOTest() throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // given
        Movie movie = new Movie();
        movie.setId(ID);
        movie.setFilename(FILENAME);
        movie.setCreatedDatetimestamp(formatter.parse(CREATED_DATETIMESTAMP));
        movie.setUpdatedDatetimestamp(formatter.parse(UPDATED_DATETIMESTAMP));

        // when
        MovieDTO movieDTO = movieMapper.movieToMovieDTO(movie);

        // then
        assertEquals(Long.valueOf(ID), movieDTO.getId());
        assertEquals(FILENAME, movieDTO.getFilename());
        assertEquals(CREATED_DATETIMESTAMP, movieDTO.getCreatedDatetimestamp());
        assertEquals(UPDATED_DATETIMESTAMP, movieDTO.getUpdatedDatetimestamp());

    }

    @Test
    public void accountDTOToAccountTest() throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        // when
        Movie movie = movieMapper.movieDTOToMovie(movieDTO);

        // then
        assertEquals(Long.valueOf(ID), movie.getId());
        assertEquals(FILENAME, movie.getFilename());
        assertEquals(formatter.parse(CREATED_DATETIMESTAMP), movie.getCreatedDatetimestamp());
        assertEquals(formatter.parse(UPDATED_DATETIMESTAMP), movie.getUpdatedDatetimestamp());

    }

}
