package com.example.ottapp.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ottapp.R;
import com.example.ottapp.data.beans.PopUpItem;
import com.example.ottapp.data.source.local.model.UITripEntity;
import com.example.ottapp.ui.adapter.PopUpAdapter;
import com.example.ottapp.ui.adapter.TripAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

public class MainFragment extends Fragment implements MainContract.View {
    public final static String LIST_STATE_KEY = "recycler_list_state";

    private MainContract.Presenter mPresenter;
    private TripAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Parcelable mListState;

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

        mAdapter = new TripAdapter(getContext(), item -> mPresenter.click(item));
        mLayoutManager = new LinearLayoutManager(getActivity());
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
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
    public void setPresenter(final MainContract.Presenter presenter) {
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
        showList(false);
        showRefreshProgress(false);
        showProgress(true);
        showStatusText(true);
        setStatusText(R.string.loading);
    }

    @Override
    public void renderRefreshingState() {
        showList(false);
        showRefreshProgress(true);
        showProgress(false);
        showStatusText(false);
    }

    @Override
    public void renderDataState(final List<UITripEntity> list) {
        showList(true);
        showProgress(false);
        showStatusText(false);
        showRefreshProgress(false);
        mAdapter.refresh(list);
        restoreListState();
    }

    @Override
    public void renderPopUpState(final ArrayList<PopUpItem> popUpList) {
        if (getActivity() != null) {
            PopUpAdapter popupAdapter = new PopUpAdapter(getActivity(), popUpList);
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.layout_popup);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            ListView listView = dialog.findViewById(R.id.listView);
            TextView textApply = dialog.findViewById(R.id.text_apply);

            textApply.setOnClickListener(v -> {
                mPresenter.setPopupPresents(false);
                dialog.dismiss();
            });

            listView.setAdapter(popupAdapter);
            dialog.show();
        }
    }

    private void init() {
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void restoreListState() {
        if (mListState != null)
            mLayoutManager.onRestoreInstanceState(mListState);
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

    private void showList(boolean show) {
        recyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
