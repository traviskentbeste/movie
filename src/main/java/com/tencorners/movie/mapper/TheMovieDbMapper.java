package com.tencorners.movie.mapper;

import com.tencorners.movie.domains.TheMovieDb;
import com.tencorners.movie.models.TheMovieDbDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TheMovieDbMapper {

    TheMovieDbMapper INSTANCE = Mappers.getMapper(TheMovieDbMapper.class);

    @Mappings({
            @Mapping(target="updatedDatetimestamp", source="theMovieDb.updatedDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss"),
            @Mapping(target="createdDatetimestamp", source="theMovieDb.createdDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss")
    })
    TheMovieDbDTO theMovieDbToTheMovieDbDTO(TheMovieDb theMovieDb);

    @Mappings({
            @Mapping(target="updatedDatetimestamp", source="theMovieDbDTO.updatedDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss"),
            @Mapping(target="createdDatetimestamp", source="theMovieDbDTO.createdDatetimestamp", dateFormat="yyyy-MM-dd HH:mm:ss")
    })
    TheMovieDb theMovieDbDTOToTheMovieDb(TheMovieDbDTO theMovieDbDTO);
    
}
