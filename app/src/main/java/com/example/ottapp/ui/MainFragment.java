package com.example.ottapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ottapp.R;
import com.example.ottapp.data.source.local.db.UITripEntity;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private TripAdapter mAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textStatus;

    public MainFragment() {

    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TripAdapter(getContext(), (pos, item) -> mPresenter.click(pos, item));
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        swipeRefreshLayout = root.findViewById(R.id.swipeToRefreshView);
        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar = root.findViewById(R.id.progressBar);
        textStatus = root.findViewById(R.id.text_status);

        init();

        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.refresh());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        mPresenter.unsubscribe();
        super.onPause();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void renderEmptyState() {
        showProgress(false);
        showStatusText(true);
        setStatusText(R.string.empty_results);
    }

    @Override
    public void renderLoadingState() {
        showProgress(true);
        showStatusText(true);
        setStatusText(R.string.loading);
    }

    @Override
    public void renderRefreshingState() {
        showRefreshProgress(false);
        showProgress(false);
        showStatusText(false);
    }

    @Override
    public void renderDataState(List<UITripEntity> list) {
        showProgress(false);
        showStatusText(false);
        showRefreshProgress(false);
        mAdapter.add(list);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    private void showRefreshProgress(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setStatusText(int textId) {
        textStatus.setText(textId);
    }

    private void showStatusText(boolean show) {
        textStatus.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
