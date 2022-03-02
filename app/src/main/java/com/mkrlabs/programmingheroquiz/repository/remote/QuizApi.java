package com.mkrlabs.programmingheroquiz.repository.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizApi{
    //https://herosapp.nyc3.digitaloceanspaces.com/

    private static  String BASE_URL = "https://herosapp.nyc3.digitaloceanspaces.com/";

    private static QuizServices quizService;

    private static QuizApi singleton;

    private QuizApi(){
        quizService = getRetrofitClient().create(QuizServices.class);
    }

    public static Retrofit getRetrofitClient (){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  static QuizApi getInstance(){
        if (singleton==null){
            singleton= new QuizApi();
        }
        return  singleton;
    }

    public QuizServices getQuizService(){
        return quizService;
    }


}
