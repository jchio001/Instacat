package com.jonathan.catfeed.ui.commons;

public interface Container {
    void onAttach();
    void overlay(Pane pane);
    boolean removeCurrentView();
    boolean isEmpty();
}
