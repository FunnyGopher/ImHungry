package com.github.funnygopher.imhungry.mortarflow;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.funnygopher.imhungry.mortar.ScreenScoper;

import flow.Services;
import flow.ServicesFactory;
import mortar.MortarScope;

public class DaggerServiceFactory extends ServicesFactory {

    private final Context context;
    private final ScreenScoper screenScoper;

    public DaggerServiceFactory(Context context) {
        this.context = context;
        screenScoper = new ScreenScoper();
    }

    @Override
    public void bindServices(@NonNull Services.Binder services) {
        MortarScope scope = screenScoper.getScreenScope(context, services.getKey().getClass().getName(), services.getKey());

        if (scope != null) {
            services.bind(services.getKey().getClass().getName(), scope);
        }
    }

    @Override
    public void tearDownServices(@NonNull Services services) {
        super.tearDownServices(services);
        MortarScope scope = screenScoper.getScreenScope(context, services.getKey().getClass().getName(), services.getKey());

        if (scope != null)
            scope.destroy();
    }
}
