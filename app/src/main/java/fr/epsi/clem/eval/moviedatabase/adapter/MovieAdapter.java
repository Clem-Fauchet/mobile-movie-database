package fr.epsi.clem.eval.moviedatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.epsi.clem.eval.moviedatabase.R;
import fr.epsi.clem.eval.moviedatabase.retrofit.response.MovieResponse;
import fr.epsi.clem.eval.moviedatabase.viewholder.MovieViewHolder;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private View.OnClickListener onClickListener;
    private List<MovieResponse> movieData;

    public MovieAdapter(List<MovieResponse> movieData) {
        this.movieData = movieData;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieView = inflater.inflate(R.layout.custom_grid_layout, parent, false);

        movieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                onClickListener.onClick(v);
            }
        });

        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResponse currentMovie = movieData.get(position);
        holder.tvTitleMovie.setText(currentMovie.getResults().get(position).getTitle());
        String stringVoteAverage = Double.toString(currentMovie.getResults().get(position).getVoteAverage());
        holder.tvRatingMovie.setText(stringVoteAverage);
        //display image with picasso
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w154" + currentMovie.getResults().get(position).getPosterPath())
                .into(holder.ivPosterMovie);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
