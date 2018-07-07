package com.arctouch.codechallenge.entity;

import com.squareup.moshi.Json;

import java.util.List;

public class UpcomingMoviesResponse {

    private int page;
    private List<Movie> results;
    @Json(name = "total_pages")
    private int totalPages;
    @Json(name = "total_results")
    private int totalResults;

    public UpcomingMoviesResponse() {
    }

    public UpcomingMoviesResponse(int page, List<Movie> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpcomingMoviesResponse)) return false;

        UpcomingMoviesResponse that = (UpcomingMoviesResponse) o;

        if (page != that.page) return false;
        if (totalPages != that.totalPages) return false;
        if (totalResults != that.totalResults) return false;
        return results != null ? results.equals(that.results) : that.results == null;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + totalPages;
        result = 31 * result + totalResults;
        return result;
    }

    @Override
    public String toString() {
        return "UpcomingMoviesResponse{" +
                "page=" + page +
                ", results=" + results +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }
}
