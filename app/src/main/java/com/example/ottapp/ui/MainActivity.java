package com.example.ottapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ottapp.R;
import com.example.ottapp.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frameContainer);

        if (mainFragment == null) {
            mainFragment = MainFragment.getInstance();

            ActivityUtils.addFragment(getSupportFragmentManager(), mainFragment, R.id.frameContainer);
        }

        new MainPresenter(mainFragment);
    }
}
