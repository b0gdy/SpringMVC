package com.example.Proiect1.services;

import com.example.Proiect1.domain.Listener;

import java.util.List;

public interface ListenerService {

    Listener save(Listener listener);

    List<Listener> findAll();

    Listener findById(Long id);

    List<Listener> findByName(String name);

    Listener update(Listener listener);

    void deleteById(Long id);

}
