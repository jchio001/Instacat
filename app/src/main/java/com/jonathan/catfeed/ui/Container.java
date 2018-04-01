package com.jonathan.catfeed.ui;

public interface Container {
    void overlay(Pane pane);
    boolean removeCurrentView();
    boolean isEmpty();
    Pane getCurrentPane();
}
