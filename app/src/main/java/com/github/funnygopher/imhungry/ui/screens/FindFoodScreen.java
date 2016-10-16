package com.github.funnygopher.imhungry.ui.screens;

import android.os.Bundle;

import com.github.funnygopher.imhungry.flow.keys.FindFoodKey;
import com.github.funnygopher.imhungry.injection.AppDependencies;
import com.github.funnygopher.imhungry.injection.scopes.DaggerScope;
import com.github.funnygopher.imhungry.model.Place;
import com.github.funnygopher.imhungry.model.database.RealmService;
import com.github.funnygopher.imhungry.mortar.ScreenComponentFactory;
import com.github.funnygopher.imhungry.ui.activities.MainActivity;
import com.github.funnygopher.imhungry.ui.views.FindFoodView;

import javax.inject.Inject;

import mortar.ViewPresenter;

public final class FindFoodScreen extends FindFoodKey implements ScreenComponentFactory<MainActivity.Component> {

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof FindFoodScreen;
    }

    @Override
    public Object createComponent(MainActivity.Component parent) {
        return DaggerFindFoodScreen_Component
                .builder()
                .component(parent)
                .build();
    }

    @dagger.Component(dependencies = MainActivity.Component.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(FindFoodView view);
    }

    @DaggerScope(Component.class)
    public static class Presenter extends ViewPresenter<FindFoodView> {

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

        public Place getRandomPlace(int value) {
            return realmService.getRandomPlace(value);
        }

        @Override
        public void dropView(FindFoodView view) {
            realmService.closeRealm();
            super.dropView(view);
        }
    }
}
