package com.example.Proiect1.services;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.FavouriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceTest {

    @Mock
    FavouriteRepository favouriteRepository;

    @InjectMocks
    FavouriteServiceImpl favouriteService;

    public Favourite createTestFavourite() {

        Listener listener = new Listener();
        listener.setId(1L);
        listener.setName("ListenerServiceTest");
        List<Listener> listeners = new ArrayList<>();
        listeners.add(listener);

        Info info = new Info();
        info.setId(1L);
        info.setFirstName("InfoFirstNameServiceTest");
        info.setLastName("InfoLastNameServiceTest");
        info.setListener(listener);

        listener.setInfo(info);

        Artist artist = new Artist();
        artist.setId(1L);
        artist.setName("ArtistServiceTest");

        Song song = new Song();
        song.setId(1L);
        song.setName("SongServiceTest");
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

        return favourite;

    }

    @Test
    public void save() {

        Favourite favourite = createTestFavourite();

        when(favouriteRepository.save(favourite)).thenReturn(favourite);

        Favourite result = favouriteService.save(favourite);

        assertEquals(favourite, result);
        verify(favouriteRepository, times(1)).save(favourite);

    }

    @Test
    void findById() {

        Favourite favourite = createTestFavourite();
        Long id = favourite.getId();

        when(favouriteRepository.findById(id)).thenReturn(Optional.of(favourite));

        Favourite result = favouriteService.findById(id);

        assertEquals(favourite, result);
        verify(favouriteRepository, times(1)).findById(id);

    }

    @Test
    public void findByIdNotFound() {

        Favourite favourite = createTestFavourite();
        Long id = favourite.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Favourite with id " + id + " not found!");

        when(favouriteRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> favouriteService.findById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findByListenerId() {

        Favourite favourite = createTestFavourite();
        Long listenerId = favourite.getListener().getId();
        List<Favourite> favourites = new ArrayList<>();
        favourites.add(favourite);

        when(favouriteRepository.findByListenerId(listenerId)).thenReturn(favourites);

        List<Favourite> result = favouriteService.findByListenerId(listenerId);

        assertEquals(favourites, result);
        verify(favouriteRepository, times(1)).findByListenerId(listenerId);

    }

    @Test
    public void findByListenerIdNotFound() {

        Favourite favourite = createTestFavourite();
        Long listenerId = favourite.getListener().getId();
        List<Favourite> favourites = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No favourite with listener id " + listenerId + " found!");

        when(favouriteRepository.findByListenerId(listenerId)).thenReturn(favourites);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> favouriteService.findByListenerId(listenerId));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findBySongId() {

        Favourite favourite = createTestFavourite();
        Long songId = favourite.getSong().getId();
        List<Favourite> favourites = new ArrayList<>();
        favourites.add(favourite);

        when(favouriteRepository.findBySongId(songId)).thenReturn(favourites);

        List<Favourite> result = favouriteService.findBySongId(songId);

        assertEquals(favourites, result);
        verify(favouriteRepository, times(1)).findBySongId(songId);

    }

    @Test
    public void findBySongIdNotFound() {

        Favourite favourite = createTestFavourite();
        Long songId = favourite.getSong().getId();
        List<Favourite> favourites = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No favourite with song id " + songId + " found!");

        when(favouriteRepository.findBySongId(songId)).thenReturn(favourites);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> favouriteService.findBySongId(songId));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    void deleteById() {

        Favourite favourite = createTestFavourite();
        Long id = favourite.getId();

        when(favouriteRepository.findById(id)).thenReturn(Optional.of(favourite));

        favouriteService.deleteById(id);

        assertThat(favouriteRepository.count()).isEqualTo(0);
        verify(favouriteRepository, times(1)).findById(id);
        verify(favouriteRepository, times(1)).deleteById(id);

    }

    @Test
    public void deleteByIdNotFound() {

        Favourite favourite = createTestFavourite();
        Long id = favourite.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Favourite with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> favouriteService.deleteById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteByListenerId() {

        Favourite favourite = createTestFavourite();
        Long listenerId = favourite.getListener().getId();
        List<Favourite> favourites = new ArrayList<>();
        favourites.add(favourite);

        when(favouriteRepository.findByListenerId(listenerId)).thenReturn(favourites);

        favouriteService.deleteByListenerId(listenerId);

        assertThat(favouriteRepository.count()).isEqualTo(0);
        verify(favouriteRepository, times(1)).findByListenerId(listenerId);
        verify(favouriteRepository, times(1)).deleteByListenerId(listenerId);

    }

    @Test
    public void deleteByListenerIdNotFound() {

        Favourite favourite = createTestFavourite();
        Long listenerId = favourite.getListener().getId();
        List<Favourite> favourites = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No favourite with listener id " + listenerId + " found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> favouriteService.deleteByListenerId(listenerId));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteBySongId() {

        Favourite favourite = createTestFavourite();
        Long songId = favourite.getSong().getId();
        List<Favourite> favourites = new ArrayList<>();
        favourites.add(favourite);

        when(favouriteRepository.findBySongId(songId)).thenReturn(favourites);

        favouriteService.deleteBySongId(songId);

        assertThat(favouriteRepository.count()).isEqualTo(0);
        verify(favouriteRepository, times(1)).findBySongId(songId);
        verify(favouriteRepository, times(1)).deleteBySongId(songId);

    }

    @Test
    public void deleteBySongIdNotFound() {

        Favourite favourite = createTestFavourite();
        Long songId = favourite.getSong().getId();
        List<Favourite> favourites = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No favourite with song id " + songId + " found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> favouriteService.deleteBySongId(songId));

        assertEquals(exception.getMessage(), result.getMessage());

    }
    
}