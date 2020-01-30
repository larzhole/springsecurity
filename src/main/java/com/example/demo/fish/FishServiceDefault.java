package com.example.demo.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishServiceDefault implements FishService {
    private final FishRepository fishRepository;

    @Autowired
    public FishServiceDefault(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    @Override
    public List<Fish> getFishes() {
        return fishRepository.findAll();
    }

    @Override
    public Fish getById(Long id) throws FishNotFoundException {
        return getFishById(id);
    }

    @Override
    public Fish addFish(Fish fish) {
        return fishRepository.save(fish);
    }

    @Override
    public void updateFish(Long id, Fish fish) throws FishNotFoundException {
        final Fish existingFish = getById(id);
        // For bigger entities, can set the ID and create/update timestamps to the 'new' fish and save
        existingFish.setFishRefName(fish.getFishRefName());
        fishRepository.save(existingFish);
    }

    @Override
    public void deleteFish(Long id) throws FishNotFoundException {
        final Fish existingFish = getById(id);
        existingFish.setIsActive(false);
        fishRepository.save(existingFish);
    }

    private Fish getFishById(Long id) throws FishNotFoundException {
        return fishRepository.findById(id)
                .orElseThrow(() -> new FishNotFoundException("Fish not found by id: " + id));
    }

}
