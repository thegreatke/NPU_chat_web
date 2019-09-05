package com.example.websocketdemo.model;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class Id {

    private String id;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}
