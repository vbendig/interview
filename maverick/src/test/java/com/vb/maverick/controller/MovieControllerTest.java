package com.vb.maverick.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vb.maverick.dto.MovieDto;
import com.vb.maverick.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@ActiveProfiles(value = "test")
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MovieService movieService;

    @InjectMocks
    MovieController movieController;

    @Test
    public void whenJsonConverterIsFound_thenReturnResponse() throws Exception {
        String url = "/api/v1/movie/1";
        testGetMovieUsingURL(url);
    }

    @Test
    public void getMovie_thenReturnResponse() throws Exception {
        String url = "/api/v1/movie/get/1";
        testGetMovieUsingURL(url);
    }

    @Test
    public void get1Movie_thenReturnResponse() throws Exception {
        String url = "/api/v1/movie/get1/1";
        testGetMovieUsingURL(url);
    }

    private void testGetMovieUsingURL(String url) throws Exception {
        MovieDto movie = MovieDto.builder().movieId(1).title("Test").imdbID("test").build();

        Mockito.when(movieService.getMovieById(anyLong())).thenReturn(movie);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"movieId\":1,\"name\":\"Test\",\"description\":\"test\"}"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
    }

    @Test
    public void testGetAllMovies() throws Exception {
        MovieDto movie = MovieDto.builder().movieId(1).title("Test").imdbID("test").build();
        String url = "/api/v1/movie/all";
        Mockito.when(movieService.getAll()).thenReturn(Arrays.asList(movie));
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"movieId\":1,\"name\":\"Test\",\"description\":\"test\"}]"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        MovieDto[] receivedDtos = mapper.readValue(content, MovieDto[].class);
        assertEquals(movie.getMovieId(), receivedDtos[0].getMovieId());

        List<MovieDto> receivedDtoList = mapper.readValue(content, new TypeReference<List<MovieDto>>(){});
        assertEquals(movie.getMovieId(), receivedDtoList.get(0).getMovieId());
    }

    @Test
    public void deleteMovies() throws Exception {
        String url = "/api/v1/movie/1";
        MvcResult result = this.mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("", content);
    }

    @Test
    public void saveMovie() throws Exception {
        MovieDto movie = MovieDto.builder().movieId(1).title("Test").imdbID("test").build();
        String url = "/api/v1/movie/save";
        MvcResult result = this.mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("", content);

    }

}

