package com.example.ottapp.ui;

import com.example.ottapp.data.beans.PopUpItem;
import com.example.ottapp.data.source.IMainRepository;
import com.example.ottapp.data.source.local.model.UITripEntity;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private final IMainRepository mRepository;
    private final CompositeDisposable mCompositeDisposable;

    private boolean mIsPopupPresents = false;
    private int mLastClickedItemId = -1;

    MainPresenter(MainContract.View view, IMainRepository repository) {
        if (view != null) {
            mView = view;
            mView.setPresenter(this);
        }

        mCompositeDisposable = new CompositeDisposable();
        mRepository = repository;
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
    public void click(final UITripEntity item) {
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
