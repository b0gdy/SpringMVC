package com.example.Proiect1.services;

import com.example.Proiect1.domain.Favourite;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    FavouriteRepository favouriteRepository;
    
    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        
        this.favouriteRepository = favouriteRepository;
        
    }
    
    @Override
    public Favourite save(Favourite favourite) {
        
        Favourite favouriteSaved = favouriteRepository.save(favourite);
        return  favouriteSaved;
        
    }
    
    @Override
    public Favourite findById(Long id) {

        Optional<Favourite> favourite = favouriteRepository.findById(id);
        if (favourite.isEmpty()) {
            throw new ResourceNotFoundException("Favourite with id " + id + " not found!");
        }
        return favourite.get();
        
    }

    @Override
    public List<Favourite> findByListenerId(Long listenerId) {

        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findByListenerId(listenerId).iterator().forEachRemaining(favourites::add);
        if(favourites.isEmpty()) {

            throw new ResourceNotFoundException("No favourite with listener id " + listenerId + " found!");

        }
        return favourites;
        
    }

    @Override
    public List<Favourite> findBySongId(Long songId) {

        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findBySongId(songId).iterator().forEachRemaining(favourites::add);
        if(favourites.isEmpty()) {

            throw new ResourceNotFoundException("No favourite with song id " + songId + " found!");

        }
        return favourites;
        
    }

    @Override
    @Transactional
    public Favourite update(Favourite favourite) {

        Optional<Favourite> favouriteOld = favouriteRepository.findById(favourite.getId());
        if(favouriteOld.isEmpty()) {
            throw new ResourceNotFoundException("Favourite with id " + favourite.getId() + " not found!");
        }
        favouriteRepository.updateById(favourite.getId(), favourite.getListener(), favourite.getSong());
        Optional<Favourite> favouriteUpdated = favouriteRepository.findById(favourite.getId());
        return favouriteUpdated.get();
        
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Favourite> favourite = favouriteRepository.findById(id);
        if(favourite.isEmpty()) {
            throw new ResourceNotFoundException("Favourite with id " + id + " not found!");
        }
        favouriteRepository.deleteById(id);
        
    }

    @Override
    @Transactional
    public void deleteByListenerId(Long listenerId) {

        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findByListenerId(listenerId).iterator().forEachRemaining(favourites::add);
        if(favourites.isEmpty()) {

            throw new ResourceNotFoundException("No favourite with listener id " + listenerId + " found!");

        }
        favouriteRepository.deleteByListenerId(listenerId);
        
    }

    @Override
    @Transactional
    public void deleteBySongId(Long songId) {

        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findBySongId(songId).iterator().forEachRemaining(favourites::add);
        if(favourites.isEmpty()) {

            throw new ResourceNotFoundException("No favourite with song id " + songId + " found!");

        }
        favouriteRepository.deleteBySongId(songId);
        
    }
    
}
