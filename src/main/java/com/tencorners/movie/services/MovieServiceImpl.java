package com.tencorners.movie.services;

import com.tencorners.movie.domains.Movie;
import com.tencorners.movie.domains.TheMovieDb;
import com.tencorners.movie.exceptions.IdNotFoundException;
import com.tencorners.movie.exceptions.ResourceNotFoundException;
import com.tencorners.movie.mapper.MovieMapper;
import com.tencorners.movie.models.MovieDTO;
import com.tencorners.movie.repositories.MovieRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieMapper movieMapper, MovieRepository movieRepository) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDTO> getAllNotFoundMovies() {
        return movieRepository.getMovieByTheMovieDbIdIsNull()
                .stream()
                .map(movie -> {
                    MovieDTO movieDTO = movieMapper.movieToMovieDTO(movie);
                    return movieDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TheMovieDb jsonObjectToTheMovieDbEntity(JSONObject jsonObject) throws ParseException {

        TheMovieDb theMovieDb = new TheMovieDb();
        theMovieDb.setId(jsonObject.getLong("id"));
        theMovieDb.setTitle(jsonObject.getString("title"));
        theMovieDb.setOverview(jsonObject.getString("overview"));
        theMovieDb.setOriginalLanguage(jsonObject.getString("original_language"));
        theMovieDb.setVideo(jsonObject.getBoolean("video"));
        theMovieDb.setTitle(jsonObject.getString("title"));
        if (jsonObject.get("poster_path").getClass() == String.class) {
            theMovieDb.setPosterPath(jsonObject.getString("poster_path"));
        }
        if (jsonObject.get("backdrop_path").getClass() == String.class) {
            theMovieDb.setBackdropPath(jsonObject.getString("backdrop_path"));
        }
        if (jsonObject.has("release_date") && (jsonObject.getString("release_date").length() > 0) ) {

            theMovieDb.setReleaseDate(formatter.parse(jsonObject.getString("release_date")));
        }
        theMovieDb.setPopularity(jsonObject.getFloat("popularity"));
        theMovieDb.setVoteAverage(jsonObject.getFloat("vote_average"));
        theMovieDb.setAdult(jsonObject.getBoolean("adult"));
        theMovieDb.setVoteAverage(jsonObject.getFloat("vote_count"));

        //System.out.println(theMovieDb);

        return theMovieDb;
    }

    @Override
    public MovieDTO getMovieByFilename(String filename) {
        return movieMapper.movieToMovieDTO(movieRepository.getMovieByFilename(filename));
    }

    @Override
    public List<MovieDTO> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(movie -> {
                    MovieDTO movieDTO = movieMapper.movieToMovieDTO(movie);
                    return movieDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public MovieDTO getMovieById(Long id) {

        return movieRepository.findById(id)
                .map(movieMapper::movieToMovieDTO)
                .orElseThrow(() -> new IdNotFoundException(id));

    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {

        return saveAndReturnDTO(movieMapper.movieDTOToMovie(movieDTO));

    }

    @Override
    public MovieDTO saveMovieByDTO(Long id, MovieDTO movieDTO) {

        Movie movie = movieMapper.movieDTOToMovie(movieDTO);
        movie.setId(id);
        return saveAndReturnDTO(movie);

    }

    @Override
    public MovieDTO patchMovie(Long id, MovieDTO movieDTO) {

        return movieRepository.findById(id)
                .map(movie -> {

                    if (movieDTO.getId() != null) {
                        movie.setId(movieDTO.getId());
                    }
                    if (movieDTO.getFilename() != null) {
                        movie.setFilename(movieDTO.getFilename());
                    }
                    if (movieDTO.getUpdatedDatetimestamp() != null) {
                        try {
                            movie.setUpdatedDatetimestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(movieDTO.getUpdatedDatetimestamp()) );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (movieDTO.getCreatedDatetimestamp() != null) {
                        try {
                            movie.setCreatedDatetimestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(movieDTO.getCreatedDatetimestamp()) );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    MovieDTO returnDTO = movieMapper.movieToMovieDTO(movieRepository.save(movie));

                    return returnDTO;

                }).orElseThrow(ResourceNotFoundException::new);

    }

    @Override
    public void deleteMovieById(Long id) {

        movieRepository.deleteById(id);

    }

    private MovieDTO saveAndReturnDTO(Movie movie) {

        Movie save = movieRepository.save(movie);
        MovieDTO returnDto = movieMapper.movieToMovieDTO(save);
        return returnDto;

    }
}
