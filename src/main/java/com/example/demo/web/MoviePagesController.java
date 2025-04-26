package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MoviePagesController {

    @GetMapping("/movies-page")
    public String moviesPage() {
        return "movies"; // src/main/resources/templates/movies.html
    }

    @GetMapping("/movie-by-id-page")
    public String movieByIdPage() {
        return "movie-by-id";
    }

    @GetMapping("/movies-by-genre-page")
    public String moviesByGenrePage() {
        return "movies-by-genre";
    }

    @GetMapping("/add-movie-page")
    public String addMoviePage() {
        return "add-movie";
    }

    @GetMapping("/update-movie-page/{id}")
    public String updateMoviePage() {
        return "update-movie";
    }

    @GetMapping("/delete-movie-page")
    public String deleteMoviePage() {
        return "delete-movie";
    }
}
