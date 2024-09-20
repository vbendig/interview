package com.vb.maverick.service;

import com.vb.maverick.dto.MovieDto;
import com.vb.maverick.mapper.MovieMapper;
import com.vb.maverick.model.Movie;
import com.vb.maverick.model.QMovie;
import com.vb.maverick.repository.MovieRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@Repository
public class MovieService {
    private final MovieRepository MovieRepository;

    private final MovieMapper MovieMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public MovieService(MovieRepository MovieRepository, MovieMapper MovieMapper) {
        this.MovieRepository = MovieRepository;
        this.MovieMapper = MovieMapper;
    }

    public List<MovieDto> getAll() {
        List<Movie> Movies = (List<Movie>) MovieRepository.findAll();
        return Movies.stream().map(MovieMapper::MovieToMovieDto).toList();
    }

    public MovieDto getMovieById(long MovieId) {
        log.info("Getting req for MovieId " + MovieId);
        Movie Movie = MovieRepository.findByMovieId(MovieId);
        return MovieMapper.MovieToMovieDto(Movie);
    }

    @Transactional
    public MovieDto saveMovie(Movie Movie) {
        boolean doesProdExist = MovieExists(Movie);
        doesProdExist &= MovieExists1(Movie);
        doesProdExist &= MovieExists2(Movie);
        if (!doesProdExist) {
            Movie = MovieRepository.save(Movie);
            return MovieMapper.MovieToMovieDto(Movie);
        } else {
            throw new IllegalArgumentException("Movie already exists");
        }
    }

    public boolean MovieExists(Movie Movie) {
        QMovie qMovie = QMovie.movie;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMovie.title.eq(Movie.getTitle()))
                        .and(qMovie.imdbID.eq(Movie.getImdbID()));
        return MovieRepository.count(builder) > 0;
    }

    public boolean MovieExists1(Movie Movie) {
        JPAQueryFactory factory = new JPAQueryFactory(entityManager);
        QMovie qMovie = QMovie.movie;
        return factory
                .selectFrom(qMovie)
                .where(qMovie.title.eq(Movie.getImdbID()), qMovie.title.eq(Movie.getTitle()))
                .stream().findAny().isPresent();

    }

    public boolean MovieExists2(Movie Movie) {
        QMovie qMovie = QMovie.movie;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qMovie.title.eq(Movie.getTitle()))
                .and(qMovie.imdbID.eq(Movie.getImdbID()));
        return MovieRepository.exists(predicate);
    }

    public void deleteMovie(long MovieId) {
        Movie Movie = MovieRepository.findByMovieId(MovieId);
        if (Movie != null) {
            MovieRepository.delete(Movie);
        }
    }
}
