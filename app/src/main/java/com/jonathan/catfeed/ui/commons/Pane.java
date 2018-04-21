package com.jonathan.catfeed.ui.commons;

public interface Pane {
    Container getContainer();
    boolean isRemovable();
    void onRemove();
}
