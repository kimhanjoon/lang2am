package com.lang2am.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String main() {
        return "table";
    }

    @RequestMapping("/cardview")
    public String card() {
        return "card";
    }

    @RequestMapping("/tableview")
    public String table() {
        return "table";
    }

}