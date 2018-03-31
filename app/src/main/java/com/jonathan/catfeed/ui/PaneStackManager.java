package com.jonathan.catfeed.ui;


import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PaneStackManager {

    private static final int DEFAULT_STACK_CAPACITY = 3;

    private static PaneStackManager instance;

    private List<List<Pane>> listOfStacks;
    private List<List<Pane>> stackOfStacks;

    public static PaneStackManager get() {
        if (instance == null) {
            synchronized (PaneStackManager.class) {
                if (instance == null) {
                    instance = new PaneStackManager().withSize(2);
                }
            }
        }

        return instance;
    }

    public PaneStackManager withSize(int size) {
        listOfStacks = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            listOfStacks.add(new ArrayList<>(DEFAULT_STACK_CAPACITY));
        }

        stackOfStacks = new ArrayList<>();

        return this;
    }

    public void addPane(int itemIndex, Pane pane) {
        List<Pane> itemIndexStack = listOfStacks.get(itemIndex);
        itemIndexStack.add(pane);

        stackOfStacks.remove(itemIndexStack);
        stackOfStacks.add(itemIndexStack);
    }

    /**
     * Do not write this code on anything that's not a side project. It is super messy.
     */
    public Pane popCurrentPane() {
        if (stackOfStacks.size() == 0) {
            return null;
        } else if (stackOfStacks.size() == 1) {
            List<Pane> topStack = stackOfStacks.get(0);
            if (topStack.get(topStack.size() - 1).isRemovable()) {
                topStack.remove(topStack.size() - 1).onRemove();
                if (topStack.isEmpty()) {
                    return null;
                } else {
                    return topStack.get(topStack.size() - 1);
                }
            } else {
                return null;
            }
        } else {
            List<Pane> topStack = stackOfStacks.get(stackOfStacks.size() - 1);
            if (topStack.get(topStack.size() - 1).isRemovable()) {
                topStack.remove(topStack.size() - 1).onRemove();
                if (topStack.isEmpty()) {
                    stackOfStacks.remove(topStack);
                    topStack =  stackOfStacks.get(stackOfStacks.size() - 1);
                    return topStack.get(topStack.size() - 1);
                } else {
                    return topStack.get(topStack.size() - 1);
                }
            } else {
                stackOfStacks.remove(topStack);
                topStack = stackOfStacks.get(stackOfStacks.size() - 1);
                return topStack.get(topStack.size() - 1);
            }
        }
    }
}
