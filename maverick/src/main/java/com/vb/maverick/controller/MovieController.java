package com.vb.maverick.controller;

import com.vb.maverick.dto.MovieDto;
import com.vb.maverick.model.Movie;
import com.vb.maverick.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<MovieDto> saveMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.saveMovie(movie));
    }
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDto> getMovie(@PathVariable long id) {
        log.info("Getting req for id" + id);
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping(path="/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MovieDto getMovie0(@PathVariable long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping(path="/get1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MovieDto getMovie1(@PathVariable long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovieDto> getAllMovies() {
        return movieService.getAll();
    }

    @DeleteMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteMovie(@PathVariable long id) {
        movieService.deleteMovie(id);
    }
}