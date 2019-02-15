package com.example.ottapp.ui;

import com.example.ottapp.base.BasePresenter;
import com.example.ottapp.base.BaseView;
import com.example.ottapp.data.beans.PopUpItem;
import com.example.ottapp.data.source.local.model.UITripEntity;

import java.util.ArrayList;
import java.util.List;

interface MainContract {
    interface View extends BaseView<Presenter> {

        void renderEmptyState();

        void renderLoadingState();

        void renderRefreshingState();

        void renderDataState(List<UITripEntity> list);

        void renderPopUpState(ArrayList<PopUpItem> popUpList);

    }

    interface Presenter extends BasePresenter {

        void refresh();

        void load();

        void readCache();

        void clearCache();

        void click(int pos, UITripEntity item);

        void setPopupPresents(boolean cond);

        boolean shouldShowPopup();

        void restorePopup();

    }
}
