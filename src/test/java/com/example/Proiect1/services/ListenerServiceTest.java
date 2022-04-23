package com.example.Proiect1.services;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.ListenerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListenerServiceTest {

    @Mock
    ListenerRepository listenerRepository;

    @InjectMocks
    ListenerServiceImpl listenerService;

    public Listener createTestListener() {

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

        return listener;

    }

    @Test
    public void save() {

        Listener listener = createTestListener();

        when(listenerRepository.save(listener)).thenReturn(listener);

        Listener result = listenerService.save(listener);

        assertEquals(listener, result);
        verify(listenerRepository, times(1)).save(listener);

    }

    @Test
    public void findAll() {

        Listener listener = createTestListener();
        List<Listener> listeners = new ArrayList<>();
        listeners.add(listener);

        when(listenerRepository.findAll()).thenReturn(listeners);

        List<Listener> result = listenerService.findAll();

        assertEquals(listeners, result);
        verify(listenerRepository, times(1)).findAll();

    }

    @Test
    public void findAllNotFound() {

        Listener listener = createTestListener();
        List<Listener> listeners = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No listener found!");

        when(listenerRepository.findAll()).thenReturn(listeners);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> listenerService.findAll());

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findById() {

        Listener listener = createTestListener();
        Long id = listener.getId();

        when(listenerRepository.findById(id)).thenReturn(Optional.of(listener));

        Listener result = listenerService.findById(id);

        assertEquals(listener, result);
        verify(listenerRepository, times(1)).findById(id);

    }

    @Test
    public void findByIdNotFound() {

        Listener listener = createTestListener();
        Long id = listener.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Listener with id " + id + " not found!");

        when(listenerRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> listenerService.findById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findByName() {

        Listener listener = createTestListener();
        String name = listener.getName();
        List<Listener> listeners = new ArrayList<>();
        listeners.add(listener);

        when(listenerRepository.findByName(name)).thenReturn(listeners);

        List<Listener> result = listenerService.findByName(name);

        assertEquals(listeners, result);
        verify(listenerRepository, times(1)).findByName(name);

    }

    @Test
    public void findByNameNotFound() {

        Listener listener = createTestListener();
        String name = listener.getName();
        List<Listener> listeners = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No listener with name " + name + " found!");

        when(listenerRepository.findByName(name)).thenReturn(listeners);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> listenerService.findByName(name));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void updateById() {

        Listener listener = createTestListener();
        Long id = listener.getId();
        String name = "ListenerServiceTestUpdated";
        Listener listenerUpdated = createTestListener();
        listenerUpdated.setName(name);

        when(listenerRepository.findById(id)).thenReturn(Optional.of(listener));
        when(listenerRepository.findById(id)).thenReturn(Optional.of(listenerUpdated));

        Listener result = listenerService.update(listenerUpdated);

        assertEquals(listenerUpdated, result);
        verify(listenerRepository, times(2)).findById(id);
        verify(listenerRepository, times(1)).updateById(id, name);

    }

    @Test
    public void updateByIdNotFound() {

        Listener listener = createTestListener();
        Long id = listener.getId();
        String name = "ListenerServiceTestUpdated";
        Listener listenerUpdated = createTestListener();
        listenerUpdated.setName(name);
        ResourceNotFoundException exception = new ResourceNotFoundException("Listener with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> listenerService.update(listenerUpdated));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteById() {

        Listener listener = createTestListener();
        Long id = listener.getId();

        when(listenerRepository.findById(id)).thenReturn(Optional.of(listener));

        listenerService.deleteById(id);

        assertThat(listenerRepository.count()).isEqualTo(0);
        verify(listenerRepository, times(1)).findById(id);
        verify(listenerRepository, times(1)).deleteById(id);

    }

    @Test
    public void deleteByIdNotFound() {

        Listener listener = createTestListener();
        Long id = listener.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Listener with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> listenerService.deleteById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

}