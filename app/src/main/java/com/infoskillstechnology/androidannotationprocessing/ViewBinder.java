package com.infoskillstechnology.androidannotationprocessing;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ViewBinder {

    public static void bind(final Activity target){
        bindViews(target, target.getClass().getDeclaredFields(),target.getWindow().getDecorView());
    }

    private static void bindViews(final Object obj, Field[] fields, View rootView) {
        for(final Field field : fields) {
            Annotation annotation = field.getAnnotation(BindView.class);
            if (annotation != null) {
                BindView bindView = (BindView) annotation;
                int id = bindView.value();
                View view = rootView.findViewById(id);
                try {
                    field.setAccessible(true);
                    field.set(obj, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
