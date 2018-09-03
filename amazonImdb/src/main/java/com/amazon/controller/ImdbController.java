package com.amazon.controller;

import com.amazon.domain.Actor;
import com.amazon.domain.Movie;
import com.amazon.exception.ResourceNotFoundException;
import com.amazon.service.ActorService;
import com.amazon.service.MovieService;
import com.amazon.utils.ParserUtilityService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by CristianZumelzu on 29-08-16.
 */
@RestController
public class ImdbController {

    @Autowired
    MovieService movieService;

    @Autowired
    ActorService actorService;

    @Autowired
    ParserUtilityService parser;

    /* This could be in other java project, just for parse data files, but for now is when the application starts */
    @PostConstruct
    public void init() {
        boolean interrupted =false;
        Future<Boolean> task1 = null;
        Future<Boolean> task2 = null;
        try {
            task2 = parser.parseMovies();
            task1 = parser.parseActors();
        } catch (InterruptedException e) {
            e.printStackTrace();
            interrupted=true;
        }
        while (true) {
            if (task1.isDone() && task2.isDone()) {
                if (!task1.isCancelled() && !task2.isCancelled() && !interrupted) parser.parseRelations();
                break;
            }
        }
    }

    @CrossOrigin
    @RequestMapping(value="/movie/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Movie getMovie(@PathVariable String id) {
        Movie movieInfo = movieService.getMovieInfo(id);
        if(movieInfo==null){
            throw new ResourceNotFoundException("Movie not found ");
        }
        return movieInfo;
    }

    @CrossOrigin
    @RequestMapping(value="/actor/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Actor getActor(@PathVariable String id) {
        Actor actorInfo = actorService.getActorInfo(id);
        if(actorInfo==null){
            throw new ResourceNotFoundException("Actor not found");
        }
        return actorInfo;
    }

    @CrossOrigin
    @RequestMapping(value="/movies/{itemPerPage}/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getMovies(@PathVariable String itemPerPage, @PathVariable String pageNumber) {
        if(!NumberUtils.isNumber(itemPerPage) || !NumberUtils.isNumber(itemPerPage)){
            throw new IllegalArgumentException();
        };
        return movieService.getMovieList(Integer.parseInt(itemPerPage),Integer.parseInt(pageNumber));
    }

    @CrossOrigin
    @RequestMapping(value="/movies/total", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> getTotalNumberOfMovies() {
        Map<String, Integer> totalNumberMovies = new HashMap<>();
        totalNumberMovies.put("totalMovies",movieService.getTotalNumberMovies());
        return totalNumberMovies;
    }
}
