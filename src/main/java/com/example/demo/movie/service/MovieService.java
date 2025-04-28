package com.example.demo.movie.service;


import com.example.demo.movie.dto.MovieDto;
import com.example.demo.movie.entity.movie;
import com.example.demo.movie.entity.movie;
import com.example.demo.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;



    public List<MovieDto> getAllMovies() {
        List<movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MovieDto getMovieById(Long id) {
        movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return convertToDto(movie);
    }

    public List<MovieDto> getMoviesByGenre(String genre) {
        List<movie> movies = movieRepository.findByGenre(genre);
        return movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MovieDto addMovie(MovieDto movieDto) {
        movie movie = convertToEntity(movieDto);
        movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setDescription(movieDto.getDescription());
        movie.setImageUrl(movieDto.getImageUrl());
        movie updatedMovie = movieRepository.save(movie);
        return convertToDto(updatedMovie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    private MovieDto convertToDto(movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .director(movie.getDirector())
                .genre(movie.getGenre())
                .releaseDate(movie.getReleaseDate())
                .description(movie.getDescription())
                .ImageUrl(movie.getImageUrl())
                .build();
    }

    private movie convertToEntity(MovieDto movieDto) {
        return movie.builder()
                .id(movieDto.getId())
                .title(movieDto.getTitle())
                .director(movieDto.getDirector())
                .genre(movieDto.getGenre())
                .releaseDate(movieDto.getReleaseDate())
                .description(movieDto.getDescription())
                .ImageUrl(movieDto.getImageUrl())
                .build();
    }
}
