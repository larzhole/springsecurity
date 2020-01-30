package com.example.demo.fish;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FishController.class)
public class FishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FishService fishService;

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
    public void getFishes() {
        final List<Fish> fishes = Collections.singletonList(testFish);

        Mockito.when(fishService.getFishes())
                .thenReturn(fishes);


    }

    @Test
    public void getFishById() {
    }

    @Test
    public void addFish() {
    }

    @Test
    public void updateFish() {
    }

    @Test
    public void deleteFish() {
    }
}