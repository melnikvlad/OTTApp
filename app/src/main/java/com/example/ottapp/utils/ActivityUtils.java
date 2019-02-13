package com.example.ottapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragment(FragmentManager fm , Fragment fragment, int containerId) {
        if (fm != null && fragment != null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(containerId, fragment);
            fragmentTransaction.commit();
        }
    }
}
