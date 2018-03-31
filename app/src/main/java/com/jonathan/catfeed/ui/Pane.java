package com.jonathan.catfeed.ui;

public interface Pane {
    Container getContainer();
    boolean isRemovable();
    void onRemove();
}
