package com.tencorners.movie.mapper;

import com.tencorners.movie.domains.Movie;
import com.tencorners.movie.models.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mappings({
            @Mapping(target="updatedDatetimestamp", source="movie.updatedDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss"),
            @Mapping(target="createdDatetimestamp", source="movie.createdDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss")
    })
    MovieDTO movieToMovieDTO(Movie movie);

    @Mappings({
            @Mapping(target="updatedDatetimestamp", source="movieDTO.updatedDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss"),
            @Mapping(target="createdDatetimestamp", source="movieDTO.createdDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss")
    })
    Movie movieDTOToMovie(MovieDTO movieDTO);
    
}
