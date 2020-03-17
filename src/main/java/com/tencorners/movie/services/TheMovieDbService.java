package com.tencorners.movie.services;

import com.tencorners.movie.models.TheMovieDbDTO;

import java.util.List;

public interface TheMovieDbService {

    List<TheMovieDbDTO> getAllTheMovieDbs();
    TheMovieDbDTO getTheMovieDbById(Long id);
    TheMovieDbDTO createTheMovieDb(TheMovieDbDTO movieDTO);
    TheMovieDbDTO saveTheMovieDbByDTO(Long id, TheMovieDbDTO movieDTO);
    TheMovieDbDTO patchTheMovieDb(Long id, TheMovieDbDTO movieDTO);
    void deleteTheMovieDbById(Long id);

}
