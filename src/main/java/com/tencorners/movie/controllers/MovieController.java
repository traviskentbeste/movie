package com.tencorners.movie.controllers;

import com.tencorners.movie.domains.TheMovieDb;
import com.tencorners.movie.mapper.TheMovieDbMapper;
import com.tencorners.movie.models.MovieDTO;
import com.tencorners.movie.models.MovieListDTO;
import com.tencorners.movie.models.TheMovieDbDTO;
import com.tencorners.movie.services.MovieService;
import com.tencorners.movie.services.TheMovieDbService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {

    @Value("${movie.base.directory}")
    private String baseDirectory;

    @Value("${themoviedb.api.key}")
    private String theMovieDbApiKey;

    private static final String API_BASE = "/api";
    private static final String API_VERSION = "v1";
    public static final String API_URL = API_BASE + "/" + API_VERSION;

    private final MovieService movieService;
    private final TheMovieDbService theMovieDbService;
    private final TheMovieDbMapper theMovieDbMapper;

    public MovieController(MovieService movieService, TheMovieDbService theMovieDbService, TheMovieDbMapper theMovieDbMapper) {
        this.movieService = movieService;
        this.theMovieDbService = theMovieDbService;
        this.theMovieDbMapper = theMovieDbMapper;
    }

    @GetMapping(value = "/movie")
    public String movie(Model model) {
        List<MovieDTO> movieDTOS = movieService.getAllNotFoundMovies();
        model.addAttribute("movieDTOS", movieDTOS);
        return "movie/index";
    }
    @GetMapping(value = "/movie/{movieId}/search")
    public String search(@PathVariable(value = "movieId") Long movieId, Model model) {
        MovieDTO movieDTO = movieService.getMovieById(movieId);
        model.addAttribute("movieDTO", movieDTO);
        return "movie/search";
    }

    @PostMapping(value = "/movie/{movieId}/search")
    public String searchPost(@RequestParam("name") String name, @PathVariable(value = "movieId") Long movieId, Model model) throws IOException, ParseException {

        name = name.replace(".", "%20");
        name = name.replace(" ", "%20");
        String sURL = "http://api.themoviedb.org/3/search/movie?api_key=" + theMovieDbApiKey + "&query=" + name;
        String jsonString = getJsonForURL(sURL);
        JSONObject json = new JSONObject(jsonString);
        JSONArray results = json.getJSONArray("results");

        List<TheMovieDb> theMovieDbList = new ArrayList<>();
        for(int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            /*
            Iterator<String> keys = result.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                System.out.println(key + " => " + result.get(key));
                if (result.get(key) instanceof JSONString) {
                }
            }
            */

            TheMovieDb theMovieDb = movieService.jsonObjectToTheMovieDbEntity(result);
            theMovieDbList.add(theMovieDb);

        }
        model.addAttribute("movieId", movieId);
        model.addAttribute("theMovieDbList", theMovieDbList);

        return "movie/the-movie-db";
    }

    @GetMapping(value = "/movie/{movieId}/add/{theMovieDbId}")
    public String add(@PathVariable(value = "movieId") Long movieId, @PathVariable(value = "theMovieDbId") Long theMovieDbId) throws ParseException, IOException {

        MovieDTO movieDTO = movieService.getMovieById(movieId);
        movieDTO.setTheMovieDbId(theMovieDbId);
        movieService.saveMovieByDTO(movieId, movieDTO);

        String sURL = "http://api.themoviedb.org/3/movie/" + theMovieDbId + "?api_key=" + theMovieDbApiKey;
        String jsonString = getJsonForURL(sURL);
        JSONObject result = new JSONObject(jsonString);
        TheMovieDb theMovieDb = movieService.jsonObjectToTheMovieDbEntity(result);
        TheMovieDbDTO theMovieDbDTO = theMovieDbMapper.theMovieDbToTheMovieDbDTO(theMovieDb);
        theMovieDbService.saveTheMovieDbByDTO(theMovieDbId, theMovieDbDTO);

        return "redirect:/movie";
    }

    @GetMapping(value = "/scan")
    public String scan() {
        return "scan/index";
    }
    @PostMapping(value = "/scan")
    public String scanPost(Model model) {
        // Creates an array in which we will store the names of files and directories
        String[] pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(baseDirectory);

        // Populates the array with names of files and directories
        pathnames = f.list();

        // For each pathname in the pathnames array
        int directories = 0;
        int files = 0;
        for (String pathname : pathnames) {
            // Print the names of files and directories
            File file = new File(baseDirectory + "/" + pathname);
            if (file.isDirectory()) {
                directories++;
                //System.out.println("dir  : " + pathname);
            } else {
                files++;
                //System.out.println("file : " + pathname);
                MovieDTO movieDTO = movieService.getMovieByFilename(pathname);
                if (movieDTO == null) {
                    movieDTO = new MovieDTO();
                    movieDTO.setFilename(pathname);
                    movieDTO = movieService.createMovie(movieDTO);
                    //System.out.println(movieDTO);
                }
            }
        }
        model.addAttribute("directories", directories);
        model.addAttribute("files", files);

        return "scan/post";
    }


    @GetMapping(value = API_URL + "/movie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieListDTO> getAllMovie() {
        return new ResponseEntity<MovieListDTO>(new MovieListDTO(movieService.getAllMovies()), HttpStatus.OK);
    }

    @GetMapping(value = API_URL + "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<MovieDTO>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @PostMapping(value = API_URL + "/movie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<MovieDTO>(movieService.createMovie(movieDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = API_URL + "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable(value = "id") Long id, @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<MovieDTO>(movieService.saveMovieByDTO(id, movieDTO), HttpStatus.OK);
    }

    @PatchMapping(value = API_URL + "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> patchMovie(@PathVariable(value = "id") Long id, @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<MovieDTO>(movieService.patchMovie(id, movieDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = API_URL + "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteMovie(@PathVariable(value = "id") Long id) {
        movieService.deleteMovieById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private String getJsonForURL(String url) throws IOException {
        int sub_debug = 0;

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + sURL);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
