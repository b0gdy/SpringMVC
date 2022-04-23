package com.example.Proiect1.controllers;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.services.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mysql")
public class SongControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SongService songService;

    @MockBean
    Model model;

    public Song createTestSong() {

        Listener listener = new Listener();
        listener.setId(1L);
        listener.setName("ListenerControllerTest");
        List<Listener> listeners = new ArrayList<>();
        listeners.add(listener);

        Info info = new Info();
        info.setId(1L);
        info.setFirstName("InfoFirstNameControllerTest");
        info.setLastName("InfoLastNameControllerTest");
        info.setListener(listener);

        listener.setInfo(info);

        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("ArtistControllerTest");

        Song song = new Song();
        song.setId(1L);
        song.setName("SongControllerTest");
        song.setGenre(Genre.Pop);
        song.setArtist(artist);
        song.setListeners(listeners);
        List<Song> songs = new ArrayList<>();
        songs.add(song);

        artist.setSongs(songs);

        listener.setSongs(songs);

        Favourite favourite = new Favourite();
        favourite.setListener(listener);
        favourite.setSong(song);
        List<Favourite> favourites = new ArrayList<>();
        favourites.add(favourite);

        song.setFavourites(favourites);

        listener.setFavourites(favourites);

        return song;

    }
    
    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songList() throws Exception {

        Song song = createTestSong();
        List<Song> songs = new ArrayList<>();
        songs.add(song);

        when(songService.findAll()).thenReturn(songs);

        mockMvc.perform(get("/song/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("songList"))
                .andExpect(model().attribute("songs", songs))
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songInfo() throws Exception {

        Long id = 1L;
        Song song = createTestSong();

        when(songService.findById(id)).thenReturn(song);

        mockMvc.perform(get("/song/info/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("songInfo"))
                .andExpect(model().attribute("song", song))
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songAdd() throws Exception {

        mockMvc.perform(get("/song/add"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songSave() throws Exception {

        mockMvc.perform(get("/song/save"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songDeleteGuest() throws Exception {

        mockMvc.perform(get("/song/delete/{id}", "1"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    void songDeleteAdmin() throws Exception {

        Long id = 1L;
        Song song = createTestSong();

        songService.deleteById(id);

        mockMvc.perform(get("/song/delete/{id}", "1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/song/list"));

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songUpdateById() throws Exception {

        mockMvc.perform(get("/song/update/{id}", "1"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void songUpdate() throws Exception {

        mockMvc.perform(get("/song/update"))
                .andExpect(status().isForbidden());

    }

}