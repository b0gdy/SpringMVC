package com.example.Proiect1.services;

import com.example.Proiect1.domain.Favourite;

import java.util.List;

public interface FavouriteService {

    Favourite save(Favourite favourite);

    Favourite findById(Long id);

    List<Favourite> findByListenerId(Long listenerId);

    List<Favourite> findBySongId(Long songId);

    Favourite update(Favourite favourite);

    void deleteById(Long id);

    void deleteByListenerId(Long listenerId);

    void deleteBySongId(Long songId);
    
}
