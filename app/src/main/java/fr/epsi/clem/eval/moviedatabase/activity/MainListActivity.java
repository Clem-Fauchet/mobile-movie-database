package fr.epsi.clem.eval.moviedatabase.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.SecureRandomSpi;
import java.util.ArrayList;
import java.util.List;

import fr.epsi.clem.eval.moviedatabase.R;
import fr.epsi.clem.eval.moviedatabase.adapter.MovieAdapter;
import fr.epsi.clem.eval.moviedatabase.retrofit.IMovieApi;
import fr.epsi.clem.eval.moviedatabase.retrofit.RetroFitClientMovie;
import fr.epsi.clem.eval.moviedatabase.retrofit.response.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainListActivity extends Activity {

    private static final String TAG = "MovieList";

    RecyclerView rvMovieList;
    MovieAdapter movieAdapter;
    List<MovieResponse> movieModelList = new ArrayList<MovieResponse>();

    //nav bar
    private BottomNavigationView btmNavBar;

    //api
    private IMovieApi movieApi;
    public static String CATEGORY;
    public static String API_KEY = "250d8c8e5fb9ff53570a2d4ed5a43657";
    public static Integer PAGE;
    public static String LANGUAGE = "en-US";

    //buttons
    private Button btSelectByRating, btSelectByPopularity, btNextPage, btPreviousPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        //nav bar
        btmNavBar = findViewById(R.id.btmNavBar);
        btmNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itHome:
                    case R.id.itLeftArrow:
                        Log.d(TAG, "MainActivity - navBar - onClick - goBackToWelcome");
                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btmNavBar.getMenu().getItem(0).setCheckable(false);
        View RightArrow = findViewById(R.id.itRightArrow);
        RightArrow.setVisibility(View.INVISIBLE);

        //buttons
        btSelectByPopularity = findViewById(R.id.btSelectByPopularity);
        btSelectByRating = findViewById(R.id.btSelectByRating);
        btNextPage = findViewById(R.id.btNextPage);
        btPreviousPage = findViewById(R.id.btPreviousPage);

        // layout movies list
        rvMovieList = findViewById(R.id.rvMovieList);
        movieAdapter = new MovieAdapter(movieModelList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        rvMovieList.setLayoutManager(gridLayoutManager);
        rvMovieList.setAdapter(movieAdapter);

        // api call
        movieApi = RetroFitClientMovie.getSingleton().create(IMovieApi.class);
        onLoadPage();

        // on click recycler view
        movieAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rvMovieList.getChildAdapterPosition(v);
                MovieResponse selectedMovie = movieModelList.get(position);

                Log.d(TAG, "MainActivity - movieAdapter - onClick - " + position + " " + selectedMovie);

                Intent intent = new Intent( MainListActivity.this, MovieDetailActivity.class);
                intent.putExtra("id", selectedMovie.getResults().get(position).getId());
                intent.putExtra("title", selectedMovie.getResults().get(position).getTitle());
                intent.putExtra("rating", selectedMovie.getResults().get(position).getVoteAverage());
                intent.putExtra("release", selectedMovie.getResults().get(position).getReleaseDate());
                intent.putExtra("overview", selectedMovie.getResults().get(position).getOverview());
                intent.putExtra("poster", "https://image.tmdb.org/t/p/w300" + selectedMovie.getResults().get(position).getPosterPath());

                startActivity(intent);
            }
        });


        //button pages
        btNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAGE = PAGE + 1;
                Log.d(TAG, "MainActivity - onClickNextPage");
                btPreviousPage.setVisibility(View.VISIBLE); // page 2 button previous reappear

                if (PAGE >= 10) {
                    btNextPage.setVisibility(View.GONE);
                } else {
                    btNextPage.setVisibility(View.VISIBLE);
                }

                generateMovies();
                System.out.println(movieModelList);
            }
        });

        btPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PAGE = PAGE - 1;
                Log.d(TAG, "MainActivity - onClickPreviousPage");
                btNextPage.setVisibility(View.VISIBLE); // page 9 button next reappear

                if (PAGE <= 1) {
                    btPreviousPage.setVisibility(View.GONE);
                } else {
                    btPreviousPage.setVisibility(View.VISIBLE);
                }

                generateMovies();
                System.out.println(movieModelList);
            }
        });

        //button filter
        btSelectByRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "MainActivity - onClickFilterRating");
                PAGE = 1;
                CATEGORY = "top_rated";

                generateMovies();
                System.out.println(movieModelList);
            }
        });

        btSelectByPopularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "MainActivity - onClickFilterPopularity");
                PAGE = 1;
                CATEGORY = "popular";

                generateMovies();
                System.out.println(movieModelList);
            }
        });
    }

    // main function call api
    public void generateMovies() {
        Call<MovieResponse> callMovie = movieApi.getMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);
        callMovie.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG, "MainActivity - onResponse");
                MovieResponse resultMovies = response.body();
                List<MovieResponse.ResultsDTO> lstOfMovies = resultMovies.getResults();

                for (int i = 0; i < lstOfMovies.size(); i++) {
                    MovieResponse.ResultsDTO resultLst = lstOfMovies.get(i);

                    MovieResponse.ResultsDTO modelDTO = new MovieResponse.ResultsDTO();
                    // for main page
                    modelDTO.setTitle(resultLst.getTitle());
                    modelDTO.setVoteAverage(resultLst.getVoteAverage());
                    modelDTO.setPosterPath("https://image.tmdb.org/t/p/w154" + resultLst.getPosterPath());
                    //for details
                    modelDTO.setReleaseDate(resultLst.getReleaseDate());
                    modelDTO.setOverview(resultLst.getOverview());

                    MovieResponse model = new MovieResponse();
                    model.setResults(lstOfMovies);
                    movieModelList.add(model);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "MainActivity - onError");
                t.printStackTrace();
            }
        });
    }

    // first function loading page
    public void onLoadPage() {
        CATEGORY = "popular";
        PAGE = 1;
        btPreviousPage.setVisibility(View.GONE); //first loading
        generateMovies();
    }
}
