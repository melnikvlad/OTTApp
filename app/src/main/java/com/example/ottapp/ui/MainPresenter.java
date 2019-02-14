package com.example.ottapp.ui;

import com.example.ottapp.data.beans.HotelUI;
import com.example.ottapp.data.source.IMainRepository;
import com.example.ottapp.data.source.local.db.UITripEntity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private final IMainRepository mRepository;
    private final CompositeDisposable mCompositeDisposable;

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
    }

    @Override
    public void click(int pos, UITripEntity item) {
        mCompositeDisposable.add(
                mRepository.getEntity(item)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> {},
                                throwable -> {}
                        )
        );
    }

    @Override
    public void save() {

    }

    @Override
    public void clearCache() {
        mCompositeDisposable.add(
                mRepository.clearCache()
                .subscribeOn(Schedulers.newThread())
                .subscribe()
        );
    }
}
