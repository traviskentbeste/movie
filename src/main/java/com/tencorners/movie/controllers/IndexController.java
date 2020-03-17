package com.tencorners.movie.controllers;

import com.tencorners.movie.domains.TheMovieDb;
import com.tencorners.movie.models.TheMovieDbDTO;
import com.tencorners.movie.services.TheMovieDbService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    private final TheMovieDbService theMovieDbService;

    public IndexController(TheMovieDbService theMovieDbService) {
        this.theMovieDbService = theMovieDbService;
    }

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {

        List<TheMovieDbDTO> theMovieDbDTOS = theMovieDbService.getAllTheMovieDbs();
        model.addAttribute("theMovieDbs", theMovieDbDTOS );
        model.addAttribute("theMovieDbsSize", theMovieDbDTOS.size() );

        return "home/index";
    }


}
