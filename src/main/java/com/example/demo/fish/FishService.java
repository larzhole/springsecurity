package com.example.demo.fish;

import java.util.List;

public interface FishService {

    List<Fish> getFishes();

    Fish getById(Long id) throws FishNotFoundException;

    Fish addFish(Fish Fish);

    void updateFish(Long id, Fish Fish) throws FishNotFoundException;

    /**
     * Soft delete
     * @param id
     * @throws FishNotFoundException
     */
    void deleteFish(Long id) throws FishNotFoundException;

}
