package com.example.Proiect1.services;

import com.example.Proiect1.domain.*;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.ArtistRepository;
import com.example.Proiect1.repositories.FavouriteRepository;
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
class SongServiceTest {

    @Mock
    SongRepository songRepository;
    
    @Mock
    FavouriteRepository favouriteRepository;

    @Mock
    ArtistRepository artistRepository;
    
    @InjectMocks
    SongServiceImpl songService;

    public Song createTestSong() {

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

        return song;

    }

    @Test
    public void save() {

        Song song = createTestSong();

        when(songRepository.save(song)).thenReturn(song);

        Song result = songService.save(song);

        assertEquals(song, result);
        verify(songRepository, times(1)).save(song);

    }

    @Test
    public void findAll() {

        Song song = createTestSong();
        List<Song> songs = new ArrayList<>();
        songs.add(song);

        when(songRepository.findAll()).thenReturn(songs);

        List<Song> result = songService.findAll();

        assertEquals(songs, result);
        verify(songRepository, times(1)).findAll();

    }

    @Test
    public void findAllNotFound() {

        Song song = createTestSong();
        List<Song> songs = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No song found!");

        when(songRepository.findAll()).thenReturn(songs);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.findAll());

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findById() {

        Song song = createTestSong();
        Long id = song.getId();

        when(songRepository.findById(id)).thenReturn(Optional.of(song));

        Song result = songService.findById(id);

        assertEquals(song, result);
        verify(songRepository, times(1)).findById(id);

    }

    @Test
    public void findByIdNotFound() {

        Song song = createTestSong();
        Long id = song.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("Song with id " + id + " not found!");

        when(songRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.findById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findByName() {

        Song song = createTestSong();
        String name = song.getName();
        List<Song> songs = new ArrayList<>();
        songs.add(song);

        when(songRepository.findByName(name)).thenReturn(songs);

        List<Song> result = songService.findByName(name);

        assertEquals(songs, result);
        verify(songRepository, times(1)).findByName(name);

    }

    @Test
    public void findByNameNotFound() {

        Song song = createTestSong();
        String name = song.getName();;
        List<Song> songs = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No song with name " + name + " found!");

        when(songRepository.findByName(name)).thenReturn(songs);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.findByName(name));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void findByArtistId() {

        Song song = createTestSong();
        Long artistId = song.getArtist().getId();
        List<Song> songs = new ArrayList<>();
        songs.add(song);

        when(songRepository.findByArtistId(artistId)).thenReturn(songs);

        List<Song> result = songService.findByArtistId(artistId);

        assertEquals(songs, result);
        verify(songRepository, times(1)).findByArtistId(artistId);

    }

    @Test
    public void findByArtistIdNotFound() {

        Song song = createTestSong();
        Long artistId = song.getArtist().getId();
        List<Song> songs = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No song with artist id " + artistId + " found!");

        when(songRepository.findByArtistId(artistId)).thenReturn(songs);

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.findByArtistId(artistId));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void updateById() {

        Song song = createTestSong();
        Long id = song.getId();
        String name = "SongServiceTestUpdated";
        Genre genre = Genre.Rap;
        Artist artist = song.getArtist();
        artist.setId(2L);
        artist.setName("ArtistServiceTestUpdated");
        Song songUpdated = createTestSong();
        songUpdated.setName(name);
        songUpdated.setGenre(genre);
        songUpdated.setArtist(artist);

        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(songRepository.findById(id)).thenReturn(Optional.of(songUpdated));

        Song result = songService.update(songUpdated);

        assertEquals(songUpdated, result);
        verify(songRepository, times(2)).findById(id);
        verify(songRepository, times(1)).updateById(id, name, genre, artist);

    }

    @Test
    public void updateByIdNotFound() {

        Song song = createTestSong();
        Long id = song.getId();
        String name = "SongServiceTestUpdated";
        Genre genre = Genre.Rap;
        Artist artist = song.getArtist();
        artist.setId(2L);
        artist.setName("ArtistServiceTestUpdated");
        Song songUpdated = createTestSong();
        songUpdated.setName(name);
        songUpdated.setGenre(genre);
        songUpdated.setArtist(artist);
        ResourceNotFoundException exception = new ResourceNotFoundException("Song with id " + id + " not found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.update(songUpdated));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteById() {

        Song song = createTestSong();
        Long id = song.getId();

        when(songRepository.findById(id)).thenReturn(Optional.of(song));

        songService.deleteById(id);

        assertThat(songRepository.count()).isEqualTo(0);
        verify(songRepository, times(1)).findById(id);
        verify(songRepository, times(1)).deleteById(id);

    }

    @Test
    public void deleteByIdNotFound() {

        Song song = createTestSong();
        Long id = song.getId();
        ResourceNotFoundException exception = new ResourceNotFoundException("No song with song id " + id + " found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.deleteById(id));

        assertEquals(exception.getMessage(), result.getMessage());

    }

    @Test
    public void deleteByArtistId() {

        Song song = createTestSong();
        Long artistId = song.getArtist().getId();
        List<Song> songs = new ArrayList<>();
        songs.add(song);

        when(songRepository.findByArtistId(artistId)).thenReturn(songs);

        songService.deleteByArtistId(artistId);

        assertThat(songRepository.count()).isEqualTo(0);
        verify(songRepository, times(1)).findByArtistId(artistId);
        verify(songRepository, times(1)).deleteByArtistId(artistId);

    }

    @Test
    public void deleteByArtistIdNotFound() {

        Song song = createTestSong();
        Long artistId = song.getArtist().getId();
        List<Song> songs = new ArrayList<>();
        ResourceNotFoundException exception = new ResourceNotFoundException("No song with artist id " + artistId + " found!");

        ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> songService.deleteByArtistId(artistId));

        assertEquals(exception.getMessage(), result.getMessage());

    }
    
}