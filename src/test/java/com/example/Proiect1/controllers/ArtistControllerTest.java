package com.example.Proiect1.controllers;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.services.ArtistService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mysql")
public class ArtistControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistService artistService;

    @MockBean
    Model model;

    public Artist createTestArtist() {

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

        return artist;

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistList() throws Exception {

        Artist artist = createTestArtist();
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);

        when(artistService.findAll()).thenReturn(artists);

        mockMvc.perform(get("/artist/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("artistList"))
                .andExpect(model().attribute("artists", artists))
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistInfo() throws Exception {

        Long id = 1L;
        Artist artist = createTestArtist();

        when(artistService.findById(id)).thenReturn(artist);

        mockMvc.perform(get("/artist/info/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("artistInfo"))
                .andExpect(model().attribute("artist", artist))
                .andExpect(content().contentType("text/html;charset=UTF-8"));

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistAdd() throws Exception {

        mockMvc.perform(get("/artist/add"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistSave() throws Exception {

        mockMvc.perform(get("/artist/save"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistDeleteGuest() throws Exception {

        mockMvc.perform(get("/artist/delete/{id}", "1"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "admin", password = "12345", roles = "ADMIN")
    void artistDeleteAdmin() throws Exception {

        Long id = 1L;
        Artist artist = createTestArtist();

        artistService.deleteById(id);

        mockMvc.perform(get("/artist/delete/{id}", "1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/artist/list"));

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistUpdateById() throws Exception {

        mockMvc.perform(get("/artist/update/{id}", "1"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "guest", password = "12345", roles = "GUEST")
    void artistUpdate() throws Exception {

        mockMvc.perform(get("/artist/update"))
                .andExpect(status().isForbidden());

    }
    
}