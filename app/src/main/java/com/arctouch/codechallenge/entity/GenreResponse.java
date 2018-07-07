package com.arctouch.codechallenge.entity;

import java.util.List;

public class GenreResponse {

    private List<Genre> genres;

    public GenreResponse() {
    }

    public GenreResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreResponse)) return false;

        GenreResponse that = (GenreResponse) o;

        return genres != null ? genres.equals(that.genres) : that.genres == null;
    }

    @Override
    public int hashCode() {
        return genres != null ? genres.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GenreResponse{" +
                "genres=" + genres +
                '}';
    }
}
