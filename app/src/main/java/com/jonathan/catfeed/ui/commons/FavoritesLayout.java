package com.jonathan.catfeed.ui.commons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.feed.FavoritesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class FavoritesLayout extends FrameLayout implements Container {

    @BindView(R.id.cat_grid_view) PaneGridView favoritesGridView;

    FavoritesAdapter favoritesAdapter = new FavoritesAdapter();

    List<Pane> paneStack = new ArrayList<>();

    public FavoritesLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(getContext(), R.layout.container_grid_layout, this);
        ButterKnife.bind(this, view);
        favoritesGridView.setAdapter(favoritesAdapter);
        paneStack.add(favoritesGridView);
    }

    @Override
    public void onAttach() {
        favoritesAdapter.maybeUpdate();
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

    @OnItemSelected(R.id.cat_grid_view)
    void onGridCellClick(int position) {
    }
}
