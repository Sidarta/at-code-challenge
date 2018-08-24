package com.arctouch.codechallenge.callback;

import com.arctouch.codechallenge.entity.Genre;

import java.util.List;

public interface OnGetGenres {
    void onGetGenresSuccessful(List<Genre> genres);
    void onGetGenresFailed(String errorMsg);
}
