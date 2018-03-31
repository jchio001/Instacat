package com.jonathan.catfeed.ui;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class SinglePaneFrameLayout extends FrameLayout implements Container {

    private static final String ONLY_ONE_CHILD = "Only 1 child view allowed.";

    public SinglePaneFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() > 1) {
            throw new IllegalStateException(ONLY_ONE_CHILD);
        }

        if (getChildCount() == 1) {
            PaneStackManager.get().addPane(0, (Pane) getChildAt(0));
        }
    }

    @Override
    public void overlay(int tabIndex, Pane pane) {
        if (getChildCount() >= 1) {
            removeAllViews();
        }

        addView((View) pane);
        PaneStackManager.get().addPane(tabIndex, pane);
    }

    @Override
    public boolean removeCurrentView() {
        View newCurrentView = (View) PaneStackManager.get().popCurrentPane();
        if (newCurrentView == null) {
            return false;
        } else {
            removeAllViews();
            addView(newCurrentView);
            return true;
        }
    }
}
