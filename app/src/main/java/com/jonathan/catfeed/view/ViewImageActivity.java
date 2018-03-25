package com.jonathan.catfeed.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.data.IntentKeys;
import com.jonathan.catfeed.data.SPUtils;
import com.jonathan.catfeed.feed.SquareImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewImageActivity extends AppCompatActivity {

    @BindView(R.id.cat_image_view) SquareImageView catImageView;
    @BindView(R.id.favorite_icon) ImageView favoriteIcon;

    private String imageId;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageId = getIntent().getStringExtra(IntentKeys.IMAGE_ID);
        isFavorite = SPUtils.isFavorite(this, imageId);

        String imageUrl = getIntent().getStringExtra(IntentKeys.IMAGE_URL);
        Picasso.get()
            .load(imageUrl)
            .into(catImageView);

        favoriteIcon.setImageResource(isFavorite ? R.drawable.ic_pink_heart
            : R.drawable.ic_heart_outline);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SPUtils.persistFavoriteState(this, imageId, isFavorite);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.favorite_icon)
    public void onFavoriteIconClick() {
        isFavorite = !isFavorite;
        favoriteIcon.setImageResource(isFavorite ? R.drawable.ic_pink_heart
            : R.drawable.ic_heart_outline);
    }
}
