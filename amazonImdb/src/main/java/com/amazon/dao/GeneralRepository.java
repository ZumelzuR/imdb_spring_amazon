package com.amazon.dao;

import com.amazon.domain.Actor;
import com.amazon.domain.Movie;

import java.util.List;
import java.util.Map;

/**
 * Created by CristianZumelzu on 30-08-16.
 */
public interface GeneralRepository {

    void insertActorsBatch(final List<Actor> actors);
    void insertMoviesBatch(final List<Movie> movies);
    void insertRelationsBatch(final List<String[]> relations);

    Actor getActor(String actor_id);
    List<Map<String, Object>>  getActors(String movie_id);

    Movie getMovie(String movie_id);
    List<Map<String, Object>>  getMovies(String actor_id);

    List<Map<String, Object>> getMovies(int itemPerPage,int pageNumber);
    int getTotalNumberMovies();
}
