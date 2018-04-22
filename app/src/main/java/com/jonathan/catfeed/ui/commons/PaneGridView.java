package com.jonathan.catfeed.ui.commons;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.jonathan.catfeed.ui.commons.Container;
import com.jonathan.catfeed.ui.commons.Pane;

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
    public void onDetach() {
    }
}
