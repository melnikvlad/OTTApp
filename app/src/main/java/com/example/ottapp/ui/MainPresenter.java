package com.example.ottapp.ui;

import com.example.ottapp.App;
import com.example.ottapp.data.source.MainRepository;
import com.example.ottapp.data.source.local.model.UIObject;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    @Inject
    MainRepository mRepository;
    @Inject
    CompositeDisposable mCompositeDisposable;
    private MainContract.View mView;

    private boolean mIsPopupPresents = false;
    private int mLastClickedItemId = -1;

    MainPresenter(MainContract.View view) {
        if (view != null) {
            mView = view;
            mView.setPresenter(this);

            App.getApp().getAppComponent().inject(this);
        }
    }

    @Override
    public void subscribe() {
        mView.renderLoadingState();
        readCache();
        restorePopup();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void load() {
        mCompositeDisposable.add(
                mRepository.loadData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> mView.renderDataState(data)
                        )
        );
    }

    @Override
    public void readCache() {
        mCompositeDisposable.add(
                mRepository.getLocalData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> mView.renderDataState(data),
                                throwable -> load()
                        )
        );
    }

    @Override
    public void refresh() {
        mView.renderRefreshingState();
        clearCache();
    }

    @Override
    public void restorePopup() {
        if (shouldShowPopup()) {
            mCompositeDisposable.add(
                    mRepository.getEntity(getLastClickedItemId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap(mRepository::preparePopupData)
                            .subscribe(
                                    popUpList -> {
                                        mView.renderPopUpState(new ArrayList<>(popUpList));
                                        setPopupPresents(true);
                                    }
                            )
            );
        }
    }

    @Override
    public void click(final UIObject item) {
        mCompositeDisposable.add(
                mRepository.preparePopupData(item)
                        .filter(list -> list != null && !list.isEmpty())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                popUpList -> {
                                    mView.renderPopUpState(new ArrayList<>(popUpList));
                                    setPopupPresents(true);
                                    setLastClickedItemId(item.getHotelId());
                                }
                        )
        );
    }

    @Override
    public void clearCache() {
        mCompositeDisposable.add(
                mRepository.clearCache()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                code -> {
                                    mView.renderLoadingState();
                                    load();
                                }
                        )
        );
    }

    @Override
    public boolean shouldShowPopup() {
        return mIsPopupPresents;
    }

    @Override
    public void setPopupPresents(boolean popupPresents) {
        mIsPopupPresents = popupPresents;
    }

    @Override
    public int getLastClickedItemId() {
        return mLastClickedItemId;
    }

    @Override
    public void setLastClickedItemId(int lastClickedItemId) {
        mLastClickedItemId = lastClickedItemId;
    }
}
