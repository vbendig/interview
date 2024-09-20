package com.vb.maverick.mapper;

import com.vb.maverick.dto.MovieDto;
import com.vb.maverick.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto MovieToMovieDto(Movie movie);
}
