package fr.epsi.clem.eval.moviedatabase.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.epsi.clem.eval.moviedatabase.R;

public class WelcomeActivity extends Activity {

    private static final String TAG = "MovieList";

    private BottomNavigationView btmNavBar;
    private ImageButton ibtStar;
    private Button btGetAllMovies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //nav bar
        btmNavBar = findViewById(R.id.btmNavBar);
        btmNavBar.setSelectedItemId(R.id.itHome);
        btmNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itHome:
                        Log.d(TAG, "WelcomeActivity - navBar - onClick - Home");
                        return true;
                    case R.id.itRightArrow:
                        Log.d(TAG, "WelcomeActivity - navBar - onClick - NextPageToMain");
                        startActivity(new Intent(getApplicationContext(),   MainListActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        View leftArrow = findViewById(R.id.itLeftArrow);
        leftArrow.setVisibility(View.INVISIBLE);

        // button listener
        ibtStar = findViewById(R.id.ibtStar);
        btGetAllMovies = findViewById(R.id.btGetAllMovies);

        ibtStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "WelcomeActivity - buttonStar - onClick");
                Intent intent = new Intent( WelcomeActivity.this, MainListActivity.class);
                startActivity(intent);
            }
        });

        btGetAllMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "WelcomeActivity - buttonGetAllMovie - onClick");
                Intent intent = new Intent( WelcomeActivity.this, MainListActivity.class);
                startActivity(intent);
            }
        });

    }
}
