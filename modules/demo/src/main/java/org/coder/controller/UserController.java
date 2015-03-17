package org.coder.controller;

import org.coder.pojo.User;
import org.rural.ui.Model;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuantao on 2014/8/7.
 */
@Controller
public class UserController {

    public String index(){
        return "forward:index.jsp";
    }

    public String showOne(Model model, HttpServletRequest request){
        System.out.println("user showOne ...");
        model.put("user", new User("zhangsan", 18));
        return "forward:test.ftl";
    }

    public String user(Model model, HttpServletRequest request){
        System.out.println("user user ...");
        model.put("user", new User("lisi", 18));
        return "forward:user/one.ftl";
    }

    public String findOne(String name, int age, Model model, HttpServletRequest request){
        System.out.println("user findOne ...");
        model.put("name", name);
        model.put("age", age);
        return "forward:user.jsp";
    }

    public String findAll(Model model) {
        System.out.println("user findAll ...");
        model.put("name", "wangyuantao");
        List<User> users = new ArrayList<User>();
        model.put("users", users);
        users.add(new User("zhangsan", 18));
        users.add(new User("lisi", 18));
        users.add(new User("wangwu", 89));
        return "json";
    }

    public static <T> T convert(Class<T> clazz, Object bean) {
        return (T)bean;
    }

}
