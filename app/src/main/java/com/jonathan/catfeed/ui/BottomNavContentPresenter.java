package com.jonathan.catfeed.ui;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BottomNavContentPresenter {

    public static final int NO_PREVIOUS_TAB_ID = -1;

    private ViewGroup parent;
    private SparseArray<Container> tabIdToContainer = new SparseArray<>();
    private List<Integer> containerStack = new ArrayList<>();

    public BottomNavContentPresenter(ViewGroup parent) {
        this.parent = parent;
    }

    public boolean contains(int tabId) {
        return tabIdToContainer.get(tabId) != null;
    }

    public void overlay(Pane pane) {
        tabIdToContainer.get(containerStack.get(containerStack.size() - 1)).overlay(pane);
    }

    public void addInitialContainer(@IdRes int tabId, Container container) {
        addContainer(tabId, container);
        containerStack.add(tabId);
    }

    public void addContainer(@IdRes int tabId, Container container) {
        tabIdToContainer.put(tabId, container);
    }

    public void focusTab(@IdRes int tabId) {
        if (containerStack.size() >= 1 && containerStack.get(containerStack.size() - 1) == tabId) {
            return;
        } else {
            containerStack.remove((Integer) tabId);
            containerStack.add(tabId);
            Container newTopContainer = tabIdToContainer.get(tabId);
            parent.removeViewAt(1);
            parent.addView((View) newTopContainer, 1);
        }
    }

    /**
     * This method is the black magic behind fighting fragments IRL. This is the method behind
     * maintaining an Instagram-like stack of stacks. Hopefully I named things not crappily so that
     * it's easy to understand what's going on.
     */
    public int removeCurrentPane() {
        if (containerStack.isEmpty()) {
            return NO_PREVIOUS_TAB_ID;
        } else {
            Container currentContainer = peekTopContainer();
            boolean removedCurrentPane = currentContainer.removeCurrentView();
            if (removedCurrentPane) {
                if (currentContainer.isEmpty()) {
                    return popCurrentContainer();
                } else {
                    return peek(containerStack);
                }
            } else {
                return popCurrentContainer();
            }
        }
    }

    public int popCurrentContainer() {
        popTop(containerStack);
        if (containerStack.isEmpty()) {
            return NO_PREVIOUS_TAB_ID;
        } else {
            int newTopContainerId = peek(containerStack);
            Container newTopContainer = tabIdToContainer.get(newTopContainerId);
            parent.removeViewAt(1);
            parent.addView((View) newTopContainer, 1);
            return newTopContainerId;
        }
    }

    private Container peekTopContainer() {
        return tabIdToContainer.get(peek(containerStack));
    }

    private<T> T peek(List<T> stack) {
        return stack.get(stack.size() - 1);
    }

    private void popTop(List stack) {
        stack.remove(stack.size() - 1);
    }
}
