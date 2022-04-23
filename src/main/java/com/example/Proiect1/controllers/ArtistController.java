package com.example.Proiect1.controllers;

import com.example.Proiect1.domain.Artist;
import com.example.Proiect1.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArtistController {

    ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {

        this.artistService = artistService;

    }

    @RequestMapping("/artist/list")
    public ModelAndView artistList() {

        ModelAndView modelAndView = new ModelAndView("artistList");
        List<Artist> artists = artistService.findAll();
        modelAndView.addObject("artists", artists);
        return modelAndView;

    }

    @GetMapping("/artist/info/{id}")
    public ModelAndView artistInfo(@PathVariable String id) {

        ModelAndView modelAndView = new ModelAndView("artistInfo");
        Artist artist = artistService.findById(Long.valueOf(id));
        modelAndView.addObject("artist", artist);
        return modelAndView;

    }

    @RequestMapping("/artist/add")
    public String artistAdd(Model model) {

        model.addAttribute("artist", new Artist());
        return "artistSave";

    }

    @PostMapping("/artist/save")
    public String artistSave(@Valid @ModelAttribute Artist artist,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "artistSave";
        }

        artistService.save(artist);
        return "redirect:/artist/list";

    }

    @RequestMapping("/artist/delete/{id}")
    public String artistDelete(@PathVariable String id) {

        artistService.deleteById(Long.valueOf(id));
        return "redirect:/artist/list";

    }

    @RequestMapping("/artist/update/{id}")
    public String artistUpdateById(@PathVariable String id, Model model) {

        Artist artist = artistService.findById(Long.valueOf(id));
        model.addAttribute("artist", artist);
        return "artistUpdate";

    }

    @PostMapping("/artist/update")
    public String artistUpdate(@Valid @ModelAttribute Artist artist,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "artistUpdate";
        }

        artistService.update(artist);
        return "redirect:/artist/list";

    }

}
