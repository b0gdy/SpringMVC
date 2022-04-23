package com.example.Proiect1.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Required field info.firstName!")
    private String firstName;

    @NotNull(message = "Required field info.lastName!")
    private String lastName;

    @OneToOne
    private Listener listener;

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", listener'=" + listener.getName() + '\'' +
                '}';
    }

}
