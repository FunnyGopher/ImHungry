package com.github.funnygopher.imhungry.mortar;

import android.content.Context;
import android.support.annotation.Nullable;

import com.github.funnygopher.imhungry.injection.DaggerService;

import mortar.MortarScope;

public class ScreenScoper {

    @Nullable
    public MortarScope getScreenScope(Context context, String name, Object key) {
        MortarScope parentScope = MortarScope.getScope(context);

        MortarScope childScope = parentScope.findChild(name);
        if (childScope != null) {
            return childScope;
        }

        if (!(key instanceof ScreenComponentFactory)) {
            return null;
        }
        ScreenComponentFactory screenComponentFactory = (ScreenComponentFactory) key;
        Object component = screenComponentFactory.createComponent(parentScope.getService(DaggerService.SERVICE_NAME));

        MortarScope.Builder builder = parentScope.buildChild().withService(DaggerService.SERVICE_NAME, component);

        return builder.build(name);
    }
}