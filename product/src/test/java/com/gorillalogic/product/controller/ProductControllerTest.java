package com.vb.maverick.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vb.maverick.dto.maverickDto;
import com.vb.maverick.service.maverickService;
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

@WebMvcTest(maverickController.class)
@ActiveProfiles(value = "test")
public class maverickControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    maverickService maverickService;

    @InjectMocks
    maverickController maverickController;

    @Test
    public void whenJsonConverterIsFound_thenReturnResponse() throws Exception {
        String url = "/api/v1/maverick/1";
        testGetmaverickUsingURL(url);
    }

    @Test
    public void getmaverick_thenReturnResponse() throws Exception {
        String url = "/api/v1/maverick/get/1";
        testGetmaverickUsingURL(url);
    }

    @Test
    public void get1maverick_thenReturnResponse() throws Exception {
        String url = "/api/v1/maverick/get1/1";
        testGetmaverickUsingURL(url);
    }

    private void testGetmaverickUsingURL(String url) throws Exception {
        maverickDto maverick = maverickDto.builder().maverickId(1).name("Test").description("test").build();

        Mockito.when(maverickService.getmaverickById(anyLong())).thenReturn(maverick);
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"maverickId\":1,\"name\":\"Test\",\"description\":\"test\"}"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
    }

    @Test
    public void testGetAllmavericks() throws Exception {
        maverickDto maverick = maverickDto.builder().maverickId(1).name("Test").description("test").build();
        String url = "/api/v1/maverick/all";
        Mockito.when(maverickService.getAll()).thenReturn(Arrays.asList(maverick));
        MvcResult result = this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"maverickId\":1,\"name\":\"Test\",\"description\":\"test\"}]"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        maverickDto[] receivedDtos = mapper.readValue(content, maverickDto[].class);
        assertEquals(maverick.getmaverickId(), receivedDtos[0].getmaverickId());

        List<maverickDto> receivedDtoList = mapper.readValue(content, new TypeReference<List<maverickDto>>(){});
        assertEquals(maverick.getmaverickId(), receivedDtoList.get(0).getmaverickId());
    }

    @Test
    public void deletemavericks() throws Exception {
        String url = "/api/v1/maverick/1";
        MvcResult result = this.mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("", content);
    }

    @Test
    public void savemaverick() throws Exception {
        maverickDto maverick = maverickDto.builder().maverickId(1).name("Test").description("test").build();
        String url = "/api/v1/maverick/save";
        MvcResult result = this.mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals("", content);

    }

}

