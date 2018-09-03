package com.amazon.service;

import com.amazon.domain.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {
    Movie getMovieInfo(String movieId);
    List<Map<String, Object>> getMovieList(int startLimit, int itemPerPage);
    public int getTotalNumberMovies();
}
