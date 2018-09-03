package com.amazon.utils.impl;

import com.amazon.dao.GeneralRepository;
import com.amazon.domain.Actor;
import com.amazon.domain.Movie;
import com.amazon.utils.ParserUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by CristianZumelzu on 30-08-16.
 */

@Service
public class ParserUtilityServiceImpl implements ParserUtilityService {

    @Autowired
    GeneralRepository generalRepository;

    private static final String joinFilePath= "data/join.data";
    private static final String actorFilePath= "data/actor";
    private static final String movieFilePath= "data/movie";

    @Async
    public Future<Boolean> parseActors() throws InterruptedException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(actorFilePath);
        File dir = new File(resource.getFile());
        File[] dirList = dir.listFiles();

        List<Actor> actors = new ArrayList<Actor>();
        if (dirList != null) {
            int counter=0;
            for (File file : dirList) {
                BufferedReader buffer = getFile(file);
                String line = null;
                if(buffer!=null){
                    try {
                        line = buffer.readLine();
                        actors.add(retrieveActorDataFromLine(line));
                        counter++;
                        if(counter%1000 == 0){
                            generalRepository.insertActorsBatch(actors);
                            actors.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(actors.size()>0) generalRepository.insertActorsBatch(actors);
        }
        return new AsyncResult<Boolean>(true);
    }

    @Async
    public Future<Boolean> parseMovies() throws InterruptedException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(movieFilePath);
        File dir = new File(resource.getFile());
        File[] dirList = dir.listFiles();

        List<Movie> movies = new ArrayList<Movie>();
        if (dirList != null) {
            int counter = 0;
            for (File file : dirList) {
                BufferedReader buffer = getFile(file);
                String line = null;
                if(buffer!=null){
                    try {
                        line = buffer.readLine();
                        movies.add(retrieveMovieDataFromLine(line));
                        counter++;
                        if(counter%1000 == 0){
                            generalRepository.insertMoviesBatch(movies);
                            movies.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(movies.size()>0) generalRepository.insertMoviesBatch(movies);
        }
        return new AsyncResult<Boolean>(true);
    }

    public void parseRelations(){
        List<String[]> relationList = new ArrayList<>();
        BufferedReader file = getFile(joinFilePath);
        if(file!=null) {
            String line = null;
            try {
                int counter = 0;
                while ((line = file.readLine()) != null) {
                    String[] dataArray = line.split("\\|");
                    if(dataArray!=null){
                        for(int i = 1;i<dataArray.length;i++) {
                            relationList.add(new String []{dataArray[0],dataArray[i]});
                            counter++;
                            if(counter%1000 == 0){
                                generalRepository.insertRelationsBatch(relationList);
                                relationList.clear();
                            }
                        }
                    }
                }
                if(relationList.size()>0) generalRepository.insertRelationsBatch(relationList);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* ---- Utils --- */
    private Movie retrieveMovieDataFromLine(String dataLine){
        String[] dataArray = dataLine.split("\\|");
        Movie movie = new Movie();
        if(dataArray!=null && dataArray.length==4){
            movie.setId(dataArray[0]);
            movie.setName(dataArray[1]);
            movie.setYear(dataArray[2]);
            movie.setDescription(dataArray[3]);
        }
        return movie;
    }

    private Actor retrieveActorDataFromLine(String dataLine){
        String[] dataArray = dataLine.split("\\|");
        Actor actor = new Actor();
        if(dataArray!=null && dataArray.length==4){
            actor.setId(dataArray[0]);
            actor.setName(dataArray[1]);
            actor.setDateBorn(unixTimeStampToStringDate(dataArray[2]));
            actor.setDescription(dataArray[3]);
        }
        return actor;
    }

    private String unixTimeStampToStringDate(String inputDate) {
        long dateNumber=Long.parseLong(inputDate);
        Date date=new Date((long)dateNumber);
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    private BufferedReader getFile(File file){
        BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        return br;
    }

    private BufferedReader getFile(String filename){
        BufferedReader br = null;
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filename);
        if(resource!=null){
            File file = new File(resource.getFile());
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return br;
    }
}
