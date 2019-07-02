package com.example.almasud.fundamental.list_and_recycler_view;

import java.util.ArrayList;

public class Movie {
    private String movieName;
    private String category;

    public Movie(String movieName, String category) {
        this.movieName = movieName;
        this.category = category;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Terminator", "Action"));
        movies.add(new Movie("Terminator 2", "Action"));
        movies.add(new Movie("Terminator 3", "Action"));

        return movies;
    }
}
