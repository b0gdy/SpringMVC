package com.example.Proiect1.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Listener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Required field listener.name!")
    private String name;

    @OneToOne(mappedBy = "listener", cascade = CascadeType.ALL, orphanRemoval = true)
    private Info info;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "listener_song",
        joinColumns = @JoinColumn(name = "listener_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"))
    private List<Song> songs;

    @OneToMany(mappedBy = "listener", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourite> favourites;

    @Override
    public String toString() {
        return "Listener{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private String  username;
    private String  password;
    private Integer enabled;


}
