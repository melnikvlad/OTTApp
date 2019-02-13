package com.example.ottapp.ui;

import com.example.ottapp.BasePresenter;
import com.example.ottapp.BaseView;
import com.example.ottapp.data.beans.HotelUI;

import java.util.List;

interface MainContract {
    interface View extends BaseView<Presenter> {

        void renderEmptyState();

        void renderLoadingState();

        void renderRefreshingState();

        void renderDataState(List<HotelUI> list);
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void load();

        void save();

        void clearCache();

        void click(int pos, HotelUI item);

    }
}
