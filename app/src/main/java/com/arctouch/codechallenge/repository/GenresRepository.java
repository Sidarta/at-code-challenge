package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.callback.OnGetGenres;

public interface GenresRepository {
    void getGenres(OnGetGenres onGetGenres);
}
