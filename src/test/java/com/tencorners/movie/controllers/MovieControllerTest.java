package com.tencorners.movie.controllers;

import com.tencorners.movie.exceptions.ResourceNotFoundException;
import com.tencorners.movie.exceptions.RestResponseEntityExceptionHandler;
import com.tencorners.movie.models.MovieDTO;
import com.tencorners.movie.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieControllerTest extends AbstractControllerTest {

    public static final Long ID = 1L;
    public static final String FILENAME = "/Volumes/movies";
    public static final String CREATED_DATETIMESTAMP = "2020-01-01 00:00:00";
    public static final String UPDATED_DATETIMESTAMP = "2020-01-02 00:00:00";

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieController movieController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

    }

    @Test
    public void testGetByNameNotFound() throws Exception {

        // given/when
        when(movieService.getMovieById(anyLong())).thenThrow(ResourceNotFoundException.class);

        // then
        mockMvc.perform(get(MovieController.API_URL + "/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllMovies() throws Exception {

        // given
        MovieDTO movieDTO1 = new MovieDTO();
        movieDTO1.setId(ID);
        movieDTO1.setFilename(FILENAME);
        movieDTO1.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO1.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        MovieDTO movieDTO2 = new MovieDTO();
        movieDTO2.setId(ID);
        movieDTO2.setFilename(FILENAME);
        movieDTO2.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO2.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        // when
        List<MovieDTO> movieDTOS = Arrays.asList(movieDTO1, movieDTO2);

        when(movieService.getAllMovies()).thenReturn(movieDTOS);

        // then
        mockMvc.perform(get(MovieController.API_URL + "/movie")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movies", hasSize(2)));

    }

    @Test
    public void testGetById() throws Exception {

        // given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        // when
        when(movieService.getMovieById(anyLong())).thenReturn(movieDTO);

        // then
        mockMvc.perform(get(MovieController.API_URL + "/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID.intValue())))
                .andExpect(jsonPath("$.filename", equalTo(FILENAME)))
                .andExpect(jsonPath("$.createdDatetimestamp", equalTo(CREATED_DATETIMESTAMP)))
                .andExpect(jsonPath("$.updatedDatetimestamp", equalTo(UPDATED_DATETIMESTAMP)))

        ;

    }

    @Test
    public void testCreateMovie() throws Exception {

        // given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);

        MovieDTO returnDTO = new MovieDTO();
        returnDTO.setId(ID);
        returnDTO.setFilename(FILENAME);
        returnDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);
        returnDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);

        when(movieService.createMovie(movieDTO)).thenReturn(returnDTO);

        // when/then
        mockMvc.perform(post(MovieController.API_URL + "/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(ID.intValue())))
                .andExpect(jsonPath("$.filename", equalTo(FILENAME)))
                .andExpect(jsonPath("$.createdDatetimestamp", equalTo(CREATED_DATETIMESTAMP)))
                .andExpect(jsonPath("$.updatedDatetimestamp", equalTo(UPDATED_DATETIMESTAMP)))
        ;

    }

    @Test
    public void testUpdateMovie() throws Exception {

        // given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        MovieDTO returnDTO = new MovieDTO();
        returnDTO.setId(ID);
        returnDTO.setFilename(FILENAME);
        returnDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        returnDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        when(movieService.saveMovieByDTO(anyLong(), any(MovieDTO.class))).thenReturn(returnDTO);

        // when/then
        mockMvc.perform(put(MovieController.API_URL + "/movie/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID.intValue())))
                .andExpect(jsonPath("$.filename", equalTo(FILENAME)))
                .andExpect(jsonPath("$.createdDatetimestamp", equalTo(CREATED_DATETIMESTAMP)))
                .andExpect(jsonPath("$.updatedDatetimestamp", equalTo(UPDATED_DATETIMESTAMP)))
        ;

    }

    @Test
    public void testPatchMovie() throws Exception {

        // given
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(ID);
        movieDTO.setFilename(FILENAME);
        movieDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        movieDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        MovieDTO returnDTO = new MovieDTO();
        returnDTO.setId(ID);
        returnDTO.setFilename(FILENAME);
        returnDTO.setCreatedDatetimestamp(CREATED_DATETIMESTAMP);
        returnDTO.setUpdatedDatetimestamp(UPDATED_DATETIMESTAMP);

        when(movieService.patchMovie(anyLong(), any(MovieDTO.class))).thenReturn(movieDTO);

        // when/then
        mockMvc.perform(patch(MovieController.API_URL + "/movie/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(movieDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID.intValue())))
                .andExpect(jsonPath("$.filename", equalTo(FILENAME)))
                .andExpect(jsonPath("$.createdDatetimestamp", equalTo(CREATED_DATETIMESTAMP)))
                .andExpect(jsonPath("$.updatedDatetimestamp", equalTo(UPDATED_DATETIMESTAMP)))
        ;

    }

    @Test
    public void testDeleteMovie() throws Exception {

        // given
        mockMvc.perform(delete(MovieController.API_URL + "/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        verify(movieService).deleteMovieById(anyLong());

    }

}
