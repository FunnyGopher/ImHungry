package com.github.funnygopher.imhungry.mortar;

public interface ScreenComponentFactory<T> {
    Object createComponent(T parent);
}
