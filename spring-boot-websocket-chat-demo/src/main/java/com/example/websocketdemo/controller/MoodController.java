package com.example.websocketdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class MoodController {



    @RequestMapping("/moodplaza")
    public String get_moodplaza(Model model){

        model.addAttribute("mood_01", "today is so beautiful day!");
        return "MoodPlaza";

    }
}
