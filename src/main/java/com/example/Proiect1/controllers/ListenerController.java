package com.example.Proiect1.controllers;

import com.example.Proiect1.domain.Favourite;
import com.example.Proiect1.domain.Info;
import com.example.Proiect1.domain.Listener;
import com.example.Proiect1.domain.Song;
import com.example.Proiect1.services.FavouriteService;
import com.example.Proiect1.services.InfoService;
import com.example.Proiect1.services.ListenerService;
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
public class ListenerController {

    ListenerService listenerService;

    InfoService infoService;

    SongService songService;

    FavouriteService favouriteService;

    @Autowired
    public ListenerController(ListenerService listenerService, InfoService infoService, SongService songService, FavouriteService favouriteService) {

        this.listenerService = listenerService;
        this.infoService = infoService;
        this.songService = songService;
        this.favouriteService = favouriteService;

    }

    @RequestMapping("/listener/list")
    public ModelAndView listenerList() {

        ModelAndView modelAndView = new ModelAndView("listenerList");
        List<Listener> listeners = listenerService.findAll();
        modelAndView.addObject("listeners", listeners);
        return modelAndView;

    }

    @GetMapping("/listener/info/{id}")
    public ModelAndView listenerInfo(@PathVariable String id) {

        ModelAndView modelAndView = new ModelAndView("listenerInfo");
        Listener listener = listenerService.findById(Long.valueOf(id));
        modelAndView.addObject("listener", listener);
        return modelAndView;

    }

    @RequestMapping("/listener/add")
    public String listenerAdd(Model model) {

        model.addAttribute("listener", new Listener());
        return "listenerSave";

    }

    @PostMapping("/listener/save")
    public String listenerSave(@Valid @ModelAttribute Listener listener,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "listenerSave";
        }

        listener.getInfo().setListener(listener);
        listenerService.save(listener);
        return "redirect:/listener/list";

    }

    @RequestMapping("/listener/delete/{id}")
    public String listenerDelete(@PathVariable String id) {

        listenerService.deleteById(Long.valueOf(id));
        return "redirect:/listener/list";

    }

    @RequestMapping("/listener/update/{id}")
    public String listenerUpdateById(@PathVariable String id, Model model) {

        Listener listener = listenerService.findById(Long.valueOf(id));
        model.addAttribute("listener", listener);
        List<Song> songsAll = songService.findAll();
        model.addAttribute("songsAll", songsAll);
        return "listenerUpdate";

    }

    @PostMapping("/listener/update")
    public String listenerUpdate(@Valid @ModelAttribute Listener listener,
                                 BindingResult bindingResult,
                                 @ModelAttribute ArrayList<Song> songsAll) {

        if (bindingResult.hasErrors()) {
            return "listenerUpdate";
        }

        Info info = infoService.findByListenerId(listener.getId());
        info.setFirstName(listener.getInfo().getFirstName());
        info.setLastName(listener.getInfo().getLastName());
        info.setListener(listener);
        infoService.update(info);
        favouriteService.deleteByListenerId(listener.getId());
        listener.getSongs().forEach(song -> {
            Favourite favourite = new Favourite();
            favourite.setListener(listener);
            favourite.setSong(song);
            favouriteService.save(favourite);
        });
        listenerService.update(listener);

        //For listener_song
        listener.setInfo(infoService.findByListenerId(listener.getId()));
        listener.setFavourites(favouriteService.findByListenerId(listener.getId()));
        listenerService.save(listener);

        return "redirect:/listener/list";

    }

}
