package com.example.Proiect1.controllers;

import com.example.Proiect1.domain.Artist;
import com.example.Proiect1.domain.Song;
import com.example.Proiect1.services.ArtistService;
import com.example.Proiect1.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SongController {

    SongService songService;

    ArtistService artistService;

    @Autowired
    public SongController(SongService songService, ArtistService artistService) {

        this.songService = songService;
        this.artistService = artistService;

    }

    @RequestMapping("/song/list")
    public ModelAndView songList() {

        ModelAndView modelAndView = new ModelAndView("songList");
        List<Song> songs = songService.findAll();
        modelAndView.addObject("songs", songs);
        return modelAndView;

    }

    @GetMapping("/song/info/{id}")
    public ModelAndView songInfo(@PathVariable String id) {

        ModelAndView modelAndView = new ModelAndView("songInfo");
        Song song = songService.findById(Long.valueOf(id));
        modelAndView.addObject("song", song);
        return modelAndView;

    }

    @RequestMapping("/song/add")
    public String songAdd(Model model) {

        model.addAttribute("song", new Song());
        List<Artist> artistsAll = artistService.findAll();
        model.addAttribute("artistsAll", artistsAll);
        return "songSave";

    }

    @PostMapping("/song/save")
    public String songSave(@Valid @ModelAttribute Song song,
                           BindingResult bindingResult,
                           @ModelAttribute ArrayList<Artist> artistsAll) {

        if (bindingResult.hasErrors()) {
            return "songSave";
        }

        songService.save(song);
        return "redirect:/song/list";

    }

    @RequestMapping("/song/delete/{id}")
    public String songDelete(@PathVariable String id) {

        songService.deleteById(Long.valueOf(id));
        return "redirect:/song/list";

    }

    @RequestMapping("/song/update/{id}")
    public String songUpdateById(@PathVariable String id, Model model) {

        Song song = songService.findById(Long.valueOf(id));
        model.addAttribute("song", song);
        List<Artist> artistsAll = artistService.findAll();
        model.addAttribute("artistsAll", artistsAll);
        return "songUpdate";

    }

    @PostMapping("/song/update")
    public String songUpdate(@Valid @ModelAttribute Song song,
                             BindingResult bindingResult,
                             @ModelAttribute ArrayList<Artist> artistsAll) {

        if (bindingResult.hasErrors()) {
            return "songUpdate";
        }

        songService.update(song);
        return "redirect:/song/list";

    }

}
