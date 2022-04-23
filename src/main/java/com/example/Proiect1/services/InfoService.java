package com.example.Proiect1.services;

import com.example.Proiect1.domain.Info;

public interface InfoService {

    Info save(Info info);

    Info findById(Long id);

    Info findByListenerId(Long listenerId);

    Info update(Info info);

    void deleteById(Long id);

    void deleteByListenerId(Long listenerId);
    
}
