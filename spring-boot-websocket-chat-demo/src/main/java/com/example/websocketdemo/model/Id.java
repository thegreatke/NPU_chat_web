package com.example.websocketdemo.model;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class Id {

    private String id;
    private String name;
    private String pagename;

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
