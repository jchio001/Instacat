package com.jonathan.catfeed.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.data.FavoritesManager;
import com.jonathan.catfeed.feed.SquareImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaneImageLayout extends FrameLayout implements Pane {

    @BindView(R.id.cat_image_view) SquareImageView catImageView;
    @BindView(R.id.favorite_icon) ImageView favoriteIcon;

    private String imageUrl;
    boolean isFavorite = false;

    public PaneImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View contentView = inflate(context, R.layout.pane_view_image, this);
        ButterKnife.bind(this, contentView);
    }

    public PaneImageLayout(@NonNull Context context,
                           @Nullable AttributeSet attrs,
                           @NonNull String imageUrl) {
        super(context, attrs);
        View contentView = inflate(context, R.layout.pane_view_image, this);
        ButterKnife.bind(this, contentView);
        withImage(imageUrl);
    }

    @Override
    public Container getContainer() {
        return (Container) getParent();
    }

    @Override
    public boolean isRemovable() {
        return true;
    }

    @Override
    public void onRemove() {
        FavoritesManager.get().persistFavoriteState(getContext(), imageUrl, isFavorite);
    }

    @OnClick(R.id.favorite_icon)
    public void onFavoriteIconClick() {
        isFavorite = !isFavorite;
        favoriteIcon.setImageResource(isFavorite ? R.drawable.ic_pink_heart
            : R.drawable.ic_heart_outline);
    }

    public PaneImageLayout withImage(String imageUrl) {
        this.imageUrl = imageUrl;

        Picasso.get().load(imageUrl).into(catImageView);

        isFavorite = FavoritesManager.get().isFavorite(imageUrl);
        favoriteIcon.setImageResource(isFavorite ? R.drawable.ic_pink_heart
            : R.drawable.ic_heart_outline);
        return this;
    }
}
