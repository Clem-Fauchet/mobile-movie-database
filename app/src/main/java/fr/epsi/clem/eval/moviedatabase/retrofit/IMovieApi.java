package fr.epsi.clem.eval.moviedatabase.retrofit;

import fr.epsi.clem.eval.moviedatabase.retrofit.response.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieApi {

    @GET("/3/movie/{category}")
    Call<MovieResponse> getMovies(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") Integer page
    );
}
