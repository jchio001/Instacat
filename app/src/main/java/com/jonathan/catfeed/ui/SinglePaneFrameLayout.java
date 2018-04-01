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

    private List<Pane> paneStack = new ArrayList<>();

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
        }

        addView((View) pane);
        paneStack.add(pane);
    }

    @Override
    public boolean removeCurrentView() {
        if (getChildCount() >= 1 && paneStack.get(paneStack.size() - 1).isRemovable()) {
            removeAllViews();
            paneStack.remove(paneStack.size() - 1).onRemove();
            addView((View) paneStack.get(paneStack.size() - 1));
            return true;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return paneStack.isEmpty();
    }

    @Override
    public Pane getCurrentPane() {
        return paneStack.get(paneStack.size() - 1);
    }
}
