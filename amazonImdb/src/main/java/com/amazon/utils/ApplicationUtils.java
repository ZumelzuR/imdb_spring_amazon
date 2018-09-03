package com.amazon.utils;

import com.amazon.domain.Actor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by CristianZumelzu on 30-08-16.
 */
public class ApplicationUtils {
    private static final String joinFilePath="data/join.data";

    public Actor readActorData(String path){

        ApplicationUtils applicationUtils = new ApplicationUtils();
        BufferedReader file = applicationUtils.getFile(path);
        Actor actor = null;
        String line = null;
        try {
            while ((line = file.readLine()) != null) {
                actor=retrieveActorData(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actor;
    }
    private Actor retrieveActorData(String dataLine){
        String[] dataArray = dataLine.split("\\|");

        Actor actor = new Actor();
        if(dataArray!=null && dataArray.length==4){
            actor.setId(dataArray[0]);
            actor.setName(dataArray[1]);
            actor.setDateBorn(unixTimeStampToStringDate(dataArray[2]));
            actor.setDescription(dataArray[3]);
          /*  if(dataArray[0]!=null && dataArray[0]!="")
                actor.setMovieList(searchActorMoviesInJoinDataFile(dataArray[0],joinFilePath));*/
        }

        return actor;
    }

    private String unixTimeStampToStringDate(String inputDate) {
        long dateNumber=Long.parseLong(inputDate);
        Date date=new Date((long)dateNumber);
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
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
        }else{
            //print not found actor
        }
        return br;
    }
    private List<String> searchActorMoviesInJoinDataFile(String actorId,String path){
        ApplicationUtils applicationUtils = new ApplicationUtils();
        BufferedReader file = applicationUtils.getFile(path);

        String line = null;
        try {
            while ((line = file.readLine()) != null) {
                if(line.contains(actorId)){
                    return getMoviesFromStringPipe(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private List<String> getMoviesFromStringPipe(String line) {
        String[] movieArray = line.split("\\|");
        List<String> movieList=null;
        if(movieArray!=null && movieArray.length>1){
            movieList= new LinkedList<String>(Arrays.asList(movieArray));
            movieList.remove(0);
        }
        return movieList;
    }
}
