package com.github.funnygopher.imhungry.injection.scopes;

import javax.inject.Scope;

@Scope
public @interface DaggerScope {
    Class<?> value();
}
