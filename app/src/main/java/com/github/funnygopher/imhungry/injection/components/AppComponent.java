package com.github.funnygopher.imhungry.injection.components;

import com.github.funnygopher.imhungry.ImHungry;
import com.github.funnygopher.imhungry.injection.modules.AppModule;
import com.github.funnygopher.imhungry.injection.scopes.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ImHungry application);
}
