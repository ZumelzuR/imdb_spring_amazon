package com.amazon.utils;

import java.util.concurrent.Future;

/**
 * Created by CristianZumelzu on 31-08-16.
 */
public interface ParserUtilityService {
    Future<Boolean> parseActors() throws InterruptedException;
    Future<Boolean> parseMovies() throws InterruptedException;
    void parseRelations();
}
