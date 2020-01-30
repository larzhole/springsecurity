package com.example.demo.fish;

import com.example.demo.utilities.annotations.DTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "fish")
public class FishController {
    private final FishService fishService;
    private final ModelMapper modelMapper;

    @Autowired
    public FishController(FishService fishService, ModelMapper modelMapper) {
        this.fishService = fishService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @DTO(FishDTO.class)
    public ResponseEntity getFishes() {
        return ResponseEntity.ok(fishService.getFishes());
    }

    @GetMapping(path = "{id}")
    @DTO(FishDTO.class)
    public ResponseEntity getFishById(@PathVariable Long id) throws FishNotFoundException {
        return ResponseEntity.ok(fishService.getById(id));
    }

    @PostMapping
    @DTO(FishDTO.class)
    public ResponseEntity addFish(@RequestBody FishDTO fishDTO) {
        return ResponseEntity.ok(dtoToEntity(fishDTO));
    }

    @PutMapping(path = "{id}")
    @DTO(FishDTO.class)
    public ResponseEntity updateFish(@PathVariable Long id,
                                     @RequestBody FishDTO fishDTO) throws FishNotFoundException {
        fishService.updateFish(id, dtoToEntity(fishDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    @DTO(FishDTO.class)
    public ResponseEntity deleteFish(@PathVariable Long id) throws FishNotFoundException {
        fishService.deleteFish(id);
        return ResponseEntity.noContent().build();
    }

    private Fish dtoToEntity(FishDTO fishDTO) {
        return modelMapper.map(fishDTO, Fish.class);
    }
}
