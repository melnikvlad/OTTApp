package com.example.ottapp.ui;

import com.example.ottapp.BasePresenter;
import com.example.ottapp.BaseView;
import com.example.ottapp.data.beans.HotelUI;
import com.example.ottapp.data.source.local.db.UITripEntity;

import java.util.List;

interface MainContract {
    interface View extends BaseView<Presenter> {

        void renderEmptyState();

        void renderLoadingState();

        void renderRefreshingState();

        void renderDataState(List<UITripEntity> list);
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void load();

        void readCache();

        void save();

        void clearCache();

        void click(int pos, UITripEntity item);

    }
}
