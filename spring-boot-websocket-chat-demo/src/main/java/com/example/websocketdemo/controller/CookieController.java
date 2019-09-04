<<<<<<< HEAD

        package com.example.websocketdemo.controller;

        import com.example.websocketdemo.model.User;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.ModelMap;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;

        import javax.servlet.http.Cookie;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
=======
package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
>>>>>>> parent of 3fc887e... Revert "123"

@Controller
public class CookieController {
    @RequestMapping("/cookie")
    public String creatCookie(@RequestParam(required=false,defaultValue="0")int state, User user, HttpServletResponse response, HttpServletRequest request, ModelMap map){
        if(user.getUsername().equals("aaa") && user.getPassword().equals("aaa")){
            //如果选择记住密码,则创建cookie,并将账号密码注入cookie
            if(state==1){
                //创建cookie对象
                Cookie ck = new Cookie(user.getUsername(), user.getPassword());
                //设置Cookie有效时间,单位为妙
                ck.setMaxAge(60*60*24);
                //设置Cookie的有效范围,/为全部路径
                ck.setPath("/");
                response.addCookie(ck);
            }else{
                //如果没有选中记住密码,则将已记住密码的cookie失效.即有效时间设为0
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals(user.getUsername())){
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
            }
            return "success";
        }else {
            map.put("ts", "用户名或密码错误");
            return "loginUser";
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> parent of 3fc887e... Revert "123"
