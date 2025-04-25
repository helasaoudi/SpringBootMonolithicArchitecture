package com.example.demo.movie.repository;

import com.example.demo.movie.entity.movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<movie, Long> {
    List<movie> findByGenre(String genre);


}

