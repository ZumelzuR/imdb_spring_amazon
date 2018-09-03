package com.amazon.service.impl;

import com.amazon.dao.GeneralRepository;
import com.amazon.domain.Actor;
import com.amazon.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActorServiceImpl implements ActorService{

    @Autowired
    private GeneralRepository repository;

    @Override
    public Actor getActorInfo(String actorId) {
        Actor actor =repository.getActor(actorId);
        if(actor!=null){
            List<Map<String, Object>> movies = repository.getMovies(actorId);
            actor.setMovieList(movies);
        }
        return actor;
    }

}e
