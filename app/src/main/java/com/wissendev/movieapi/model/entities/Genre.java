package com.wissendev.movieapi.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Genre {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
