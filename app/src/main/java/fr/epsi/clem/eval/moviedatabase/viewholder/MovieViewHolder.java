package fr.epsi.clem.eval.moviedatabase.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.epsi.clem.eval.moviedatabase.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTitleMovie;
    public TextView tvRatingMovie;
    public ImageView ivPosterMovie;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitleMovie = itemView.findViewById(R.id.tvTitleMovie);
        tvRatingMovie = itemView.findViewById(R.id.tvRatingMovie);
        ivPosterMovie = itemView.findViewById(R.id.ivPosterMovie);
    }
}
