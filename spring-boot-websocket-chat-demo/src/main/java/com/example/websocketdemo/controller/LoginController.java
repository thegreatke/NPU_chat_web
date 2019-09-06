package com.example.websocketdemo.controller;


import com.example.websocketdemo.model.Id;
import com.example.websocketdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    private Id id_class;

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam("userName") String userName,
                         @RequestParam("password") String password) {

        int result;
         result =   userService.login(userName,password);
        if (result == 1) return "login successfully!";
        else if (result == 0) return "username does not exist";
        else return "password is wrong or errors";
    }



    @PostMapping("/regist")
    @ResponseBody
    public String regist(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                         @RequestParam("gender") String gender) {

        int result;
        result =   userService.regist(userName,password,gender);
        if (result == 1) return "regist successfully!";
        else if (result == 0) return "username is already exist, change one";
        else return "errors!";
    }

    @GetMapping("/setid")
    @ResponseBody
    public String xuqiu(@RequestParam("id") String id){

        id_class.setId(id);
        return id;
    }

    @GetMapping("/getid")
    @ResponseBody
    public String xuqiu(){


        return id_class.getId();
    }

    @GetMapping("/setname")
    @ResponseBody
    public String xuqiu_02(@RequestParam("name") String name){

        id_class.setName(name);
        return name;
    }

    @GetMapping("/getname")
    @ResponseBody
    public String xuqiu_02(){

        return id_class.getName();
    }


}