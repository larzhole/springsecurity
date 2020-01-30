package com.example.demo.fish;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class FishServiceDefaultTest {

    @Mock
    private FishRepository fishRepository;

    @InjectMocks
    private FishServiceDefault fishServiceDefault;

    private static final long ID = 100L;
    private Fish testFish;

    @Before
    public void setUp() {
        testFish = new Fish();
        testFish.setFishRefName("clown fish");
    }

    @Test
    public void getFishes() {
        final List<Fish> fishes = Collections.singletonList(testFish);

        Mockito.when(fishRepository.findAll())
                .thenReturn(fishes);

        Assert.assertEquals(fishes, fishServiceDefault.getFishes());
    }

    @Test
    public void getById() throws FishNotFoundException {
        testFish.setFishRefSysId(ID);

        Mockito.when(fishRepository.findById(ID))
                .thenReturn(Optional.of(testFish));

        Assert.assertEquals(testFish, fishServiceDefault.getById(ID));
    }

    @Test(expected = FishNotFoundException.class)
    public void getById_FishNotFound() throws FishNotFoundException {
        fishServiceDefault.getById(ID);
    }

    @Test
    public void addFish() {
        Mockito.when(fishRepository.save(testFish))
                .thenReturn(testFish);

        Assert.assertEquals(testFish, fishServiceDefault.addFish(testFish));
    }

    @Test
    public void updateFish() throws FishNotFoundException {
        Mockito.when(fishRepository.findById(ID))
                .thenReturn(Optional.of(testFish));

        testFish.setFishRefName("not clown fish");

        fishServiceDefault.updateFish(ID, testFish);
    }

    @Test(expected = FishNotFoundException.class)
    public void updateFish_FishNotFound() throws FishNotFoundException {
        fishServiceDefault.updateFish(ID, testFish);
    }

    @Test
    public void deleteFish() throws FishNotFoundException {
        Mockito.when(fishRepository.findById(ID))
                .thenReturn(Optional.of(testFish));

        fishServiceDefault.deleteFish(ID);
    }

    @Test(expected = FishNotFoundException.class)
    public void deleteFish_FishNotFound() throws FishNotFoundException {
        fishServiceDefault.deleteFish(ID);
    }
}