package com.github.funnygopher.imhungry.injection.modules;

import android.content.Context;

import com.github.funnygopher.imhungry.injection.scopes.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @PerApplication
    Context provideAppContext() {
        return context;
    }
}
