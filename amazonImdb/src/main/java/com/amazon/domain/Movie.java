package com.amazon.domain;

import java.util.List;
import java.util.Map;

public class Movie {

    private String id;
    private String name;
    private String year;
    private String description;
    private List<Map<String, Object>> actorList;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Map<String, Object>> getActorList() {
        return actorList;
    }

    public void setActorList(List<Map<String, Object>> actorList) {
        this.actorList = actorList;
    }
}
