package com.example.Proiect1.services;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.ArtistRepository;
import com.example.Proiect1.repositories.SongRepository;
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
class ArtistServiceTest {

    @Mock
    ArtistRepository artistRepository;

    @Mock
    SongRepository songRepository;

    @InjectMocks
    ArtistServiceImpl artistService;

    public Artist createTestArtist() {

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

        return artist;

    }

    @Test
    public void save() {

        Artist artist = createTestArtist();

        when(artistRepository.save(artist)).thenReturn(artist);

        Artist result = artistService.save(artist);

        assertEquals(artist, result);
        verify(artistRepository, times(1)).save(artist);

    }

    @Test
    public void findAll() {

        Artist artist = createTestArtist();
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);

        when(artistRepository.findAll()).thenReturn(artists);

        List<Artist> result = artistService.findAll();

        assertEquals(artists, result);
        verify(artistRepository, times(1)).findAll();

    }

    @Test
    public void findAllNotFound() {

        Artist artist = createTestArtist();
        List<Artist> artists = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No artist found!");

        when(artistRepository.findAll()).thenReturn(artists);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> artistService.findAll());

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findById() {

        Artist artist = createTestArtist();
        Long id = artist.getId();

        when(artistRepository.findById(id)).thenReturn(Optional.of(artist));

        Artist result = artistService.findById(id);

        assertEquals(artist, result);
        verify(artistRepository, times(1)).findById(id);

    }

    @Test
    public void findByIdNotFound() {

        Artist artist = createTestArtist();
        Long id = artist.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Artist with id " + id + " not found!");

        when(artistRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> artistService.findById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findByName() {

        Artist artist = createTestArtist();
        String name = artist.getName();
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);

        when(artistRepository.findByName(name)).thenReturn(artists);

        List<Artist> result = artistService.findByName(name);

        assertEquals(artists, result);
        verify(artistRepository, times(1)).findByName(name);

    }

    @Test
    public void findByNameNotFound() {

        Artist artist = createTestArtist();
        String name = artist.getName();
        List<Artist> artists = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No artist with name " + name + " found!");

        when(artistRepository.findByName(name)).thenReturn(artists);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> artistService.findByName(name));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void updateById() {

        Artist artist = createTestArtist();
        Long id = artist.getId();
        String name = "ArtistServiceTestUpdated";
        Artist artistUpdated = createTestArtist();
        artistUpdated.setName(name);

        when(artistRepository.findById(id)).thenReturn(Optional.of(artist));
        when(artistRepository.findById(id)).thenReturn(Optional.of(artistUpdated));

        Artist result = artistService.update(artistUpdated);

        assertEquals(artistUpdated, result);
        verify(artistRepository, times(2)).findById(id);
        verify(artistRepository, times(1)).updateById(id, name);

    }

    @Test
    public void updateByIdNotFound() {

        Artist artist = createTestArtist();
        Long id = artist.getId();
        String name = "ArtistServiceTestUpdated";
        Artist artistUpdated = createTestArtist();
        artistUpdated.setName(name);
        ResourceNotFoundException exception = new ResourceNotFoundException("Artist with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> artistService.update(artist));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteById() {

        Artist artist = createTestArtist();
        Long id = artist.getId();

        when(artistRepository.findById(id)).thenReturn(Optional.of(artist));

        artistService.deleteById(id);

        assertThat(artistRepository.count()).isEqualTo(0);
        verify(artistRepository, times(1)).findById(id);
        verify(artistRepository, times(1)).deleteById(id);

    }

    @Test
    public void deleteByIdNotFound() {

        Artist artist = createTestArtist();
        Long id = artist.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Artist with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> artistService.deleteById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }
    
}