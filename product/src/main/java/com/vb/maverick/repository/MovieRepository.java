package com.vb.maverick.repository;

import com.vb.maverick.model.Movie;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>, QuerydslPredicateExecutor<Movie>{
    Movie findByMovieId(long maverickId);

}
