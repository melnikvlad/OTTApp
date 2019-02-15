package com.example.ottapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ottapp.R;
import com.example.ottapp.data.source.MainRepository;
import com.example.ottapp.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private static final String POPUP_VISIBILITY_STATE = "is popup shown";
    private static final String POPUP_ITEM_ID = "item id to restore";

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frameContainer);

        if (mainFragment == null) {
            mainFragment = MainFragment.getInstance();

            ActivityUtils.addFragment(getSupportFragmentManager(), mainFragment, R.id.frameContainer);
        }

        mPresenter = new MainPresenter(mainFragment, MainRepository.getInstance());

        if (savedInstanceState != null) {
            mPresenter.setPopupPresents(savedInstanceState.getBoolean(POPUP_VISIBILITY_STATE));
            mPresenter.setLastClickedItemId(savedInstanceState.getInt(POPUP_ITEM_ID));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(POPUP_VISIBILITY_STATE, mPresenter.shouldShowPopup());
        outState.putInt(POPUP_ITEM_ID, mPresenter.getLastClickedItemId());

        super.onSaveInstanceState(outState);
    }
}
