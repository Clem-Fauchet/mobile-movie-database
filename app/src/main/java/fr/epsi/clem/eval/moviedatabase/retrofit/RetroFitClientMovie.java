package fr.epsi.clem.eval.moviedatabase.retrofit;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClientMovie {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org";

    //singleton
    public static Retrofit getSingleton() {
        if (retrofit == null) {
            //create instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
