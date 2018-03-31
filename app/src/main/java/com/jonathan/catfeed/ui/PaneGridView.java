package com.jonathan.catfeed.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class PaneGridView extends GridView implements Pane {

    public PaneGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Container getContainer() {
        return (Container) getParent();
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void onRemove() {
    }
}
