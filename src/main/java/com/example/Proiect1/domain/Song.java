package com.example.Proiect1.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Required field song.name!")
    private String name;

    @ManyToMany(mappedBy = "songs", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Listener> listeners;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToOne
    private Artist artist;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> favourites;

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre'=" + genre.getDescription() + '\'' +
                ", artist'=" + artist.getName() + '\'' +
                '}';
    }

}
