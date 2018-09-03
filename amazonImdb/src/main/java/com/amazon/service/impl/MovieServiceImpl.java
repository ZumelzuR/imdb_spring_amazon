package com.amazon.service.impl;

import com.amazon.dao.GeneralRepository;
import com.amazon.domain.Movie;
import com.amazon.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private GeneralRepository repository;

    public Movie getMovieInfo(String movieId) {
        Movie movie =repository.getMovie(movieId);
        if(movie!=null){
            List<Map<String, Object>> actors = repository.getActors(movieId);
            movie.setActorList(actors);
        }
        return movie;
    }

    public List<Map<String, Object>> getMovieList(int itemPerPage,int pageNumber){
       return repository.getMovies(itemPerPage,pageNumber);
    }

    public int getTotalNumberMovies(){
        return repository.getTotalNumberMovies();
    }
}
