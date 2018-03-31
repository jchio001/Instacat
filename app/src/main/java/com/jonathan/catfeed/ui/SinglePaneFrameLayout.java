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

    private static final int DEFAULT_CAPACITY = 10;

    List<Pane> paneStack = new ArrayList<>(DEFAULT_CAPACITY);

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
            paneStack.add((Pane) getChildAt(0));
        }
    }

    @Override
    public void overlay(Pane pane) {
        if (getChildCount() >= 1) {
            removeAllViews();
            paneStack.get(paneStack.size() - 1).onRemove();
        }

        addView((View) pane);
        paneStack.add(pane);
    }

    @Override
    public boolean removeCurrentView() {
        if (paneStack.size() > 0 && paneStack.get(paneStack.size() - 1).isRemovable()) {
            removeAllViews();
            Pane prevPane = paneStack.remove(paneStack.size() - 1);
            prevPane.onRemove();

            if (paneStack.size() > 0) {
                addView((View) paneStack.get(paneStack.size() - 1));
            }

            return true;
        }

        return false;
    }
}
