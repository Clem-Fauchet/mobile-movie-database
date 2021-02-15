package fr.epsi.clem.eval.moviedatabase.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import fr.epsi.clem.eval.moviedatabase.R;

public class MovieDetailActivity extends Activity {

    private static final String TAG = "MovieList";

    private ImageView ivPosterDetail;
    private TextView tvRatingDetail, tvTitleDetail, tvOverviewDetail, tvReleaseDetail;

    //nav bar
    private BottomNavigationView btmNavBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //nav bar
        btmNavBar = findViewById(R.id.btmNavBar);
        btmNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itLeftArrow:
                        Log.d(TAG, "DetailActivity - navBar - onClick - goBackToMain");
                        startActivity(new Intent(getApplicationContext(), MainListActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itHome:
                        Log.d(TAG, "DetailActivity - navBar - onClick - goBackToHome");
                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        View RightArrow = findViewById(R.id.itRightArrow);
        RightArrow.setVisibility(View.INVISIBLE);

        // details
        ivPosterDetail = findViewById(R.id.ivPosterDetail);
        tvRatingDetail = findViewById(R.id.tvRatingDetail);
        tvTitleDetail = findViewById(R.id.tvTitleDetail);
        tvOverviewDetail = findViewById(R.id.tvOverviewDetail);
        tvReleaseDetail = findViewById(R.id.tvReleaseDetail);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {

            String title = extra.getString("title");
            Double dRating = extra.getDouble("rating");
            String release = extra.getString("release");
            String overview = extra.getString("overview");
            String poster = extra.getString("poster");

            String rating = String.valueOf(dRating);

            tvTitleDetail.setText(title);
            tvRatingDetail.setText(rating);
            tvOverviewDetail.setText(overview);
            tvReleaseDetail.setText(release);

            Picasso.get().load(poster).into(ivPosterDetail);
        }
    }
}
