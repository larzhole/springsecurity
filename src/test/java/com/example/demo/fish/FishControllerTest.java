package com.example.demo.fish;

import com.example.demo.utilities.web.DtoMapperResponseBodyAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FishController.class)
public class FishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FishService fishService;

    // Mock DtoMapperResponseBodyAdvice and ModelMapper for easy unit testing
    // Fully test it through integration tests
    @MockBean
    private DtoMapperResponseBodyAdvice dtoMapperResponseBodyAdvice;

    @MockBean
    private ModelMapper modelMapper;

    private static Fish testFish;
    private static final String URL = "/fish";
    private static final long ID = 100L;

    @BeforeClass
    public static void beforeClass() {
        testFish = new Fish();
        testFish.setFishRefName("walleye");
        testFish.setFishRefSysId(ID);
    }

    @Test
    public void getFishes() throws Exception {
        final List<Fish> fishes = Collections.singletonList(testFish);
        final String content = new ObjectMapper().writeValueAsString(fishes);

        Mockito.when(fishService.getFishes())
                .thenReturn(fishes);

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    public void getFishById() throws Exception {
        final String content = new ObjectMapper().writeValueAsString(testFish);

        Mockito.when(fishService.getById(ID))
                .thenReturn(testFish);

        mockMvc.perform(get(URL + "/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    public void addFish() throws Exception {
        final String payload = new ObjectMapper().writeValueAsString(new ModelMapper().map(testFish, FishDTO.class));
        final String json = new ObjectMapper().writeValueAsString(testFish);

        Mockito.when(fishService.addFish(Mockito.any()))
                .thenReturn(testFish);

        mockMvc.perform(post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void updateFish() throws Exception {
        final String payload = new ObjectMapper().writeValueAsString(new ModelMapper().map(testFish, FishDTO.class));

        mockMvc.perform(put(URL + "/100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateFish_FishNotFound() throws Exception {
        final String payload = new ObjectMapper().writeValueAsString(new ModelMapper().map(testFish, FishDTO.class));

        Mockito.doThrow(FishNotFoundException.class)
                .when(fishService).updateFish(Mockito.anyLong(), Mockito.any());

        mockMvc.perform(put(URL + "/100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFish() throws Exception {
        mockMvc.perform(delete(URL + "/100"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteFish_FishNotFound() throws Exception {

        Mockito.doThrow(FishNotFoundException.class)
                .when(fishService).deleteFish(100L);

        mockMvc.perform(delete(URL + "/100"))
                .andExpect(status().isNotFound());
    }
}