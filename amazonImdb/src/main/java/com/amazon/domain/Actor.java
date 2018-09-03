package com.amazon.domain;

import java.util.List;
import java.util.Map;

public class Actor {

    private String id;
    private String name;
    private String dateBorn;
    private String description;
    private List<Map<String, Object>> movieList;

    public Actor(){}

    public Actor(String id, String name, String dateBorn, String description){
        this.id=id;
        this.name=name;
        this.dateBorn=dateBorn;
        this.description=description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(String dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Map<String, Object>> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Map<String, Object>> movieList) {
        this.movieList = movieList;
    }
}
