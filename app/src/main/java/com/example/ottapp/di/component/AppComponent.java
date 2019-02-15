package com.example.ottapp.di.component;

import com.example.ottapp.di.module.AppModule;
import com.example.ottapp.di.module.LocalSourceModule;
import com.example.ottapp.di.module.RemoteDataSourceModule;
import com.example.ottapp.ui.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        LocalSourceModule.class,
        RemoteDataSourceModule.class
})
@Singleton
public interface AppComponent {
    void inject(MainPresenter presenter);
}
