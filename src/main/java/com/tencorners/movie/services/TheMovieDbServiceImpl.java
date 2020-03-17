package com.tencorners.movie.services;

import com.tencorners.movie.domains.TheMovieDb;
import com.tencorners.movie.exceptions.IdNotFoundException;
import com.tencorners.movie.exceptions.ResourceNotFoundException;
import com.tencorners.movie.mapper.TheMovieDbMapper;
import com.tencorners.movie.models.TheMovieDbDTO;
import com.tencorners.movie.repositories.TheMovieDbRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheMovieDbServiceImpl implements TheMovieDbService {

    private final TheMovieDbMapper theMovieDbMapper;
    private final TheMovieDbRepository theMovieDbRepository;

    public TheMovieDbServiceImpl(TheMovieDbMapper theMovieDbMapper, TheMovieDbRepository theMovieDbRepository) {
        this.theMovieDbMapper = theMovieDbMapper;
        this.theMovieDbRepository = theMovieDbRepository;
    }

    @Override
    public List<TheMovieDbDTO> getAllTheMovieDbs() {

        return theMovieDbRepository.findAll()
                .stream()
                .map(movie -> {
                    TheMovieDbDTO movieDTO = theMovieDbMapper.theMovieDbToTheMovieDbDTO(movie);
                    return movieDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public TheMovieDbDTO getTheMovieDbById(Long id) {

        return theMovieDbRepository.findById(id)
                .map(theMovieDbMapper::theMovieDbToTheMovieDbDTO)
                .orElseThrow(() -> new IdNotFoundException(id));

    }

    @Override
    public TheMovieDbDTO createTheMovieDb(TheMovieDbDTO movieDTO) {

        return saveAndReturnDTO(theMovieDbMapper.theMovieDbDTOToTheMovieDb(movieDTO));

    }

    @Override
    public TheMovieDbDTO saveTheMovieDbByDTO(Long id, TheMovieDbDTO theMovieDbDTO) {

        TheMovieDb theMovieDb = theMovieDbMapper.theMovieDbDTOToTheMovieDb(theMovieDbDTO);
        theMovieDb.setId(id);
        return saveAndReturnDTO(theMovieDb);

    }

    @Override
    public TheMovieDbDTO patchTheMovieDb(Long id, TheMovieDbDTO theMovieDbDTO) {

        return theMovieDbRepository.findById(id)
                .map(theMovieDb -> {

                    if (theMovieDbDTO.getId() != null) {
                        theMovieDb.setId(theMovieDbDTO.getId());
                    }
                    /*
                    if (theMovieDbDTO.getFilename() != null) {
                        theMovieDb.setFilename(theMovieDbDTO.getFilename());
                    }
                    if (theMovieDbDTO.getUpdatedDatetimestamp() != null) {
                        try {
                            theMovieDb.setUpdatedDatetimestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(theMovieDbDTO.getUpdatedDatetimestamp()) );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (theMovieDbDTO.getCreatedDatetimestamp() != null) {
                        try {
                            theMovieDb.setCreatedDatetimestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(theMovieDbDTO.getCreatedDatetimestamp()) );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    */
                    TheMovieDbDTO returnDTO = theMovieDbMapper.theMovieDbToTheMovieDbDTO(theMovieDbRepository.save(theMovieDb));

                    return returnDTO;

                }).orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public void deleteTheMovieDbById(Long id) {

        theMovieDbRepository.deleteById(id);

    }

    private TheMovieDbDTO saveAndReturnDTO(TheMovieDb movie) {

        TheMovieDb save = theMovieDbRepository.save(movie);
        TheMovieDbDTO returnDto = theMovieDbMapper.theMovieDbToTheMovieDbDTO(save);
        return returnDto;

    }
}
