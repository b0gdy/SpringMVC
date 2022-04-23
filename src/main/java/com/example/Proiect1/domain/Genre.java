package com.example.Proiect1.domain;

public enum Genre {

    Pop("Pop"), Rock("Rock"), Rap("Rap");

    private String description;

    public String getDescription() {

        return description;

    }

    Genre(String description) {

        this.description = description;

    }

}
