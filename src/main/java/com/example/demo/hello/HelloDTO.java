package com.example.demo.hello;

import com.example.demo.fish.Fish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HelloDTO {
    private String message;
    private List<Fish> fishes;
}
