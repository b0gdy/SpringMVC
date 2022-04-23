package com.example.Proiect1.services;

import com.example.Proiect1.domain.Artist;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.ArtistRepository;
import com.example.Proiect1.repositories.FavouriteRepository;
import com.example.Proiect1.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    ArtistRepository artistRepository;

    SongRepository songRepository;

    FavouriteRepository favouriteRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository, FavouriteRepository favouriteRepository) {

        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.favouriteRepository = favouriteRepository;

    }

    @Override
    public Artist save(Artist artist) {

        Artist artistSaved = artistRepository.save(artist);
        return artistSaved;

    }

    @Override
    public List<Artist> findAll() {

        List<Artist> artists = new ArrayList<>();
        artistRepository.findAll().iterator().forEachRemaining(artists::add);
        if(artists.isEmpty()) {

            throw new ResourceNotFoundException("No artist found!");

        }
        return artists;

    }

    @Override
    public Artist findById(Long id) {

        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isEmpty()) {
            throw new ResourceNotFoundException("Artist with id " + id + " not found!");
        }
        return artist.get();

    }

    @Override
    public List<Artist> findByName(String name) {

        List<Artist> artists = new ArrayList<>();
        artistRepository.findByName(name).iterator().forEachRemaining(artists::add);
        if(artists.isEmpty()) {

            throw new ResourceNotFoundException("No artist with name " + name + " found!");

        }
        return artists;

    }

    @Override
    @Transactional
    public Artist update(Artist artist) {

        Optional<Artist> artistOld = artistRepository.findById(artist.getId());
        if(artistOld.isEmpty()) {
            throw new ResourceNotFoundException("Artist with id " + artist.getId() + " not found!");
        }
        artistRepository.updateById(artist.getId(), artist.getName());
        Optional<Artist> artistUpdated = artistRepository.findById(artist.getId());
        return artistUpdated.get();

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isEmpty()) {
            throw new ResourceNotFoundException("Artist with id " + id + " not found!");
        }
        artist.get().getSongs().forEach(song -> {
            song.setListeners(null);
            favouriteRepository.deleteBySongId(song.getId());
        });
        songRepository.deleteByArtistId(id);
        artistRepository.deleteById(id);

    }
    
}
