package com.github.funnygopher.imhungry.injection;

import android.content.Context;
import android.support.annotation.NonNull;

public class DaggerService {

    public static final String SERVICE_NAME = DaggerService.class.getName();

    /**
     * Caller is required to know the type of the component for this context.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDaggerComponent(@NonNull Context context) {
        //noinspection ResourceType
        return (T) context.getSystemService(SERVICE_NAME);
    }
}