package com.example.Proiect1.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Listener listener;

    @ManyToOne
    private Song song;

    @Override
    public String toString() {
        return "Favourite{" +
                "id=" + id +
                ", listener'=" + listener + '\'' +
                ", song'=" + song.getName() + '\'' +
                '}';
    }

}
