/*
 * ******************************************************************************
 * Copyright (c) 2019 or the year of first publication, if earlier, HighQ Solutions Limited or its licensors
 * ******************************************************************************
 */

package com.highq.nativeautomationlibrary.utils.commonutility;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class RecyclerViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}