package com.jonathan.catfeed.ui;

public interface Container {
    void overlay(int tabIndex, Pane pane);
    boolean removeCurrentView();
}
