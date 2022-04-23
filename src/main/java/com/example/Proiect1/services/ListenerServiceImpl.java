package com.example.Proiect1.services;

import com.example.Proiect1.domain.Listener;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.FavouriteRepository;
import com.example.Proiect1.repositories.InfoRepository;
import com.example.Proiect1.repositories.ListenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListenerServiceImpl implements ListenerService {

    ListenerRepository listenerRepository;

    InfoRepository infoRepository;

    FavouriteRepository favouriteRepository;

    @Autowired
    public ListenerServiceImpl(ListenerRepository listenerRepository, InfoRepository infoRepository, FavouriteRepository favouriteRepository) {

        this.listenerRepository = listenerRepository;
        this.infoRepository = infoRepository;
        this.favouriteRepository = favouriteRepository;

    }

    @Override
    public Listener save(Listener listener) {

        Listener listenerSaved = listenerRepository.save(listener);
        return listenerSaved;

    }

    @Override
    public List<Listener> findAll() {

        List<Listener> listeners = new ArrayList<>();
        listenerRepository.findAll().iterator().forEachRemaining(listeners::add);
        if(listeners.isEmpty()) {

            throw new ResourceNotFoundException("No listener found!");

        }
        return listeners;

    }

    @Override
    public Listener findById(Long id) {

        Optional<Listener> listener = listenerRepository.findById(id);
        if (listener.isEmpty()) {
            throw new ResourceNotFoundException("Listener with id " + id + " not found!");
        }
        return listener.get();

    }

    @Override
    public List<Listener> findByName(String name) {

        List<Listener> listeners = new ArrayList<>();
        listenerRepository.findByName(name).iterator().forEachRemaining(listeners::add);
        if(listeners.isEmpty()) {

            throw new ResourceNotFoundException("No listener with name " + name + " found!");

        }
        return listeners;

    }

    @Override
    @Transactional
    public Listener update(Listener listener) {

        Optional<Listener> listenerOld = listenerRepository.findById(listener.getId());
        if(listenerOld.isEmpty()) {
            throw new ResourceNotFoundException("Listener with id " + listener.getId() + " not found!");
        }
        listenerRepository.updateById(listener.getId(), listener.getName());
        Optional<Listener> listenerUpdated = listenerRepository.findById(listener.getId());
        return listenerUpdated.get();

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Listener> listener = listenerRepository.findById(id);
        if(listener.isEmpty()) {
            throw new ResourceNotFoundException("Listener with id " + id + " not found!");
        }
        infoRepository.deleteByListenerId(id);
        listener.get().setSongs(null);
        favouriteRepository.deleteByListenerId(id);
        listenerRepository.deleteById(id);

    }

}
