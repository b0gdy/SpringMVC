package com.example.Proiect1.services;

import com.example.Proiect1.domain.Info;
import com.example.Proiect1.exceptions.ResourceNotFoundException;
import com.example.Proiect1.repositories.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InfoServiceImpl implements InfoService {

    InfoRepository infoRepository;

    @Autowired
    public InfoServiceImpl(InfoRepository infoRepository) {

        this.infoRepository = infoRepository;

    }

    @Override
    public Info save(Info info) {

        Info infoSaved = infoRepository.save(info);
        return infoSaved;

    }

    @Override
    public Info findById(Long id) {

        Optional<Info> info = infoRepository.findById(id);
        if (info.isEmpty()) {
            throw new ResourceNotFoundException("Info with id " + id + " not found!");
        }
        return info.get();
        
    }

    @Override
    public Info findByListenerId(Long listenerId) {

        Optional<Info> info = infoRepository.findByListenerId(listenerId);
        if (info.isEmpty()) {
            throw new ResourceNotFoundException("Info with listener id " + listenerId + " not found!");
        }
        return info.get();

    }

    @Override
    @Transactional
    public Info update(Info info) {

        Optional<Info> infoOld = infoRepository.findById(info.getId());
        if(infoOld.isEmpty()) {
            throw new ResourceNotFoundException("Info with id " + info.getId() + " not found!");
        }
        infoRepository.updateById(info.getId(), info.getFirstName(), info.getLastName(), info.getListener());
        Optional<Info> infoUpdated = infoRepository.findById(info.getId());
        return infoUpdated.get();
        
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Info> info = infoRepository.findById(id);
        if(info.isEmpty()) {
            throw new ResourceNotFoundException("Info with id " + id + " not found!");
        }
        infoRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void deleteByListenerId(Long listenerId) {

        Optional<Info> info = infoRepository.findByListenerId(listenerId);
        if (info.isEmpty()) {

            throw new ResourceNotFoundException("No info with listener id " + listenerId + " found!");

        }
        infoRepository.deleteByListenerId(listenerId);

    }

}
