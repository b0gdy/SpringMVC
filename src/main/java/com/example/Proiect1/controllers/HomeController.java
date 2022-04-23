package com.example.Proiect1.controllers;

import com.example.Proiect1.domain.Song;
import com.example.Proiect1.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    SongService songService;

    @Autowired
    public HomeController(SongService songService) {

        this.songService = songService;

    }

    @GetMapping("/showLogInForm")
    public String showLogInForm() {

        return "login";

    }

    @RequestMapping({"", "/", "/index"})
    public ModelAndView songList() {

        ModelAndView modelAndView = new ModelAndView("songList");
        List<Song> songs = songService.findAll();
        modelAndView.addObject("songs", songs);
        return modelAndView;

    }

    @GetMapping("/login-error")
    public String loginError() {

        return "login-error";

    }

    @GetMapping("/access_denied")
    public String accessDenied() {

        return "access_denied";

    }

}
