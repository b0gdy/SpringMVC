package com.example.Proiect1.services;

import com.example.Proiect1.domain.Song;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.FavouriteRepository;
import com.example.Proiect1.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    SongRepository songRepository;
    
    FavouriteRepository favouriteRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, FavouriteRepository favouriteRepository) {

        this.songRepository = songRepository;
        this.favouriteRepository = favouriteRepository;

    }

    @Override
    public Song save(Song song) {

        Song songSaved = songRepository.save(song);
        return songSaved;

    }

    @Override
    public List<Song> findAll() {

        List<Song> songs = new ArrayList<>();
        songRepository.findAll().iterator().forEachRemaining(songs::add);
        if(songs.isEmpty()) {

            throw new ResourceNotFoundException("No song found!");

        }
        return songs;

    }

    @Override
    public Song findById(Long id) {

        Optional<Song> song = songRepository.findById(id);
        if (song.isEmpty()) {
            throw new ResourceNotFoundException("Song with id " + id + " not found!");
        }
        return song.get();

    }

    @Override
    public List<Song> findByName(String name) {

        List<Song> songs = new ArrayList<>();
        songRepository.findByName(name).iterator().forEachRemaining(songs::add);
        if(songs.isEmpty()) {

            throw new ResourceNotFoundException("No song with name " + name + " found!");

        }
        return songs;

    }

    @Override
    public List<Song> findByArtistId(Long artistId) {

        List<Song> songs = new ArrayList<>();
        songRepository.findByArtistId(artistId).iterator().forEachRemaining(songs::add);
        if(songs.isEmpty()) {

            throw new ResourceNotFoundException("No song with artist id " + artistId + " found!");

        }
        return songs;

    }

    @Override
    @Transactional
    public Song update(Song song) {

        Optional<Song> songOld = songRepository.findById(song.getId());
        if(songOld.isEmpty()) {
            throw new ResourceNotFoundException("Song with id " + song.getId() + " not found!");
        }
        songRepository.updateById(song.getId(), song.getName(), song.getGenre(), song.getArtist());
        Optional<Song> songUpdated = songRepository.findById(song.getId());
        return songUpdated.get();

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Song> song = songRepository.findById(id);
        if(song.isEmpty()) {

            throw new ResourceNotFoundException("No song with song id " + id + " found!");

        }
        song.get().setListeners(null);
        favouriteRepository.deleteBySongId(id);
        songRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void deleteByArtistId(Long artistId) {

        List<Song> songs = new ArrayList<>();
        songRepository.findByArtistId(artistId).iterator().forEachRemaining(songs::add);
        if(songs.isEmpty()) {

            throw new ResourceNotFoundException("No song with artist id " + artistId + " found!");

        }
        songs.forEach(song -> {
            song.setListeners(null);
            favouriteRepository.deleteBySongId(song.getId());
        });
        songRepository.deleteByArtistId(artistId);
    }
    
}
