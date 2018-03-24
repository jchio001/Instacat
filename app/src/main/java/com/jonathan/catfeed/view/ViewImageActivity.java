package com.jonathan.catfeed.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jonathan.catfeed.R;
import com.jonathan.catfeed.data.IntentKeys;
import com.jonathan.catfeed.feed.SquareImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewImageActivity extends AppCompatActivity {

    @BindView(R.id.cat_image_view) SquareImageView catImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String imageUrl = getIntent().getStringExtra(IntentKeys.IMAGE_URL);
        Picasso.get()
            .load(imageUrl)
            .into(catImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
