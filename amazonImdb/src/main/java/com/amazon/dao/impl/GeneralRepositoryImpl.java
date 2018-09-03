package com.amazon.dao.impl;

import com.amazon.dao.GeneralRepository;
import com.amazon.domain.Actor;
import com.amazon.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CristianZumelzu on 30-08-16.
 */

@Repository
public class GeneralRepositoryImpl implements GeneralRepository {

    @Autowired
    protected JdbcTemplate jdbc;

    public void insertActorsBatch(final List<Actor> actors){

        String sql = "INSERT IGNORE INTO ACTOR (ID, NAME, DATE_BORN, DESCRIPTION) VALUES (?, ?, ?, ?)";

        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Actor actor = actors.get(i);
                ps.setString(1, actor.getId());
                ps.setString(2, actor.getName());
                ps.setString(3, actor.getDateBorn());
                ps.setString(4, actor.getDescription());
            }

            @Override
            public int getBatchSize() {
                return actors.size();
            }
        });
    }

    public void insertMoviesBatch(final List<Movie> movies){

        String sql = "INSERT IGNORE INTO MOVIE (ID, NAME, YEAR, DESCRIPTION) VALUES (?, ?, ?, ?)";

        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Movie movie = movies.get(i);
                ps.setString(1, movie.getId());
                ps.setString(2, movie.getName());
                ps.setString(3, movie.getYear());
                ps.setString(4, movie.getDescription());
            }

            @Override
            public int getBatchSize() {
                return movies.size();
            }
        });
    }
    public void insertRelationsBatch(final List<String[]> relationList){

        String sql = "INSERT IGNORE INTO ACTOR_MOVIE (ACTOR_ID, MOVIE_ID) VALUES (?, ?)";

        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String [] relation = relationList.get(i);
                ps.setString(1, relation[0]);
                ps.setString(2, relation[1]);
            }

            @Override
            public int getBatchSize() {
                return relationList.size();
            }
        });
    }

    public Actor getActor(String actor_id) {
        Actor actor= null;
        try{
           actor= (Actor)jdbc.queryForObject("SELECT * FROM actor WHERE id=?", new Object[] { actor_id },  new BeanPropertyRowMapper(Actor.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return actor;
    }

    public Movie getMovie(String movie_id) {
        Movie movie= null;
        try{
            movie = (Movie) jdbc.queryForObject("SELECT * FROM movie WHERE id=?", new Object[]{movie_id}, new BeanPropertyRowMapper(Movie.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return movie;

    }
    public List<Map<String, Object>> getActors(String movie_id) {
        List<Map<String, Object>> actors = new ArrayList<>();
        try{
            actors = jdbc.queryForList("SELECT r.actor_id,a.name FROM amazon_imdb.actor_movie r,amazon_imdb.actor a where r.movie_id=? and r.actor_id=a.id;", movie_id);
        } catch (EmptyResultDataAccessException e) {
            return actors;
        }
        return actors;
    }
    // ver q ond esto casos extremos
    public  List<Map<String, Object>> getMovies(String actor_id) {
        List<Map<String, Object>> movies = new ArrayList<>();
        try{
            movies = jdbc.queryForList("SELECT r.movie_id,m.name FROM amazon_imdb.actor_movie r,amazon_imdb.movie m WHERE r.actor_id=? and r.movie_id=m.id;", actor_id);
        } catch (EmptyResultDataAccessException e) {
            return movies;
        }
        return movies;
    }

    @Override
    public List<Map<String, Object>> getMovies(int itemPerPage, int pageNumber) {
        List<Map<String, Object>> movies = new ArrayList<>();
        int calculatedLimit= (pageNumber-1)*itemPerPage;
        try{
            movies = jdbc.queryForList("SELECT id,name FROM movie LIMIT ?,?", calculatedLimit,itemPerPage);
        } catch (EmptyResultDataAccessException e) {
            return movies;
        }
        return movies;
    }

    public int getTotalNumberMovies() {
        int movies=0;
        try{
            movies = jdbc.queryForObject("SELECT count(*) FROM movie", Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return movies;
        }
        return movies;
    }
}