package com.arctouch.codechallenge.ui.adapter.presenter.implementation;

import com.arctouch.codechallenge.ui.adapter.presenter.HomeAdapterPresenter;

public class HomeAdapterPresenterImplementation implements HomeAdapterPresenter{

    HomeAdapterPresenter.View mView;

    public HomeAdapterPresenterImplementation(View view) {
        this.mView = view;
    }
}
