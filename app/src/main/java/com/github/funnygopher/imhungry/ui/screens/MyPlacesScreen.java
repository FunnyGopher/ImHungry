package com.github.funnygopher.imhungry.ui.screens;

import android.os.Bundle;

import com.github.funnygopher.imhungry.flow.keys.MyPlacesKey;
import com.github.funnygopher.imhungry.injection.AppDependencies;
import com.github.funnygopher.imhungry.injection.scopes.DaggerScope;
import com.github.funnygopher.imhungry.model.database.RealmService;
import com.github.funnygopher.imhungry.mortar.ScreenComponentFactory;
import com.github.funnygopher.imhungry.ui.activities.MainActivity;
import com.github.funnygopher.imhungry.ui.views.MyPlacesView;

import javax.inject.Inject;

import io.realm.Realm;
import mortar.ViewPresenter;

public class MyPlacesScreen extends MyPlacesKey implements ScreenComponentFactory<MainActivity.Component> {

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof MyPlacesScreen;
    }

    @Override
    public Object createComponent(MainActivity.Component parent) {
        return DaggerMyPlacesScreen_Component
                .builder()
                .component(parent)
                .build();
    }

    @dagger.Component(dependencies = MainActivity.Component.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(MyPlacesView view);
    }

    @DaggerScope(Component.class)
    public static class Presenter extends ViewPresenter<MyPlacesView> {

        RealmService realmService;

        @Inject
        public Presenter() {
            // default constructor
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            realmService = new RealmService();
        }

        public Realm getData() {
            return realmService.getRealmInstance();
        }

        @Override
        public void dropView(MyPlacesView view) {
            realmService.closeRealm();
            super.dropView(view);
        }
    }
}
