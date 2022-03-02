package com.mkrlabs.programmingheroquiz.repository.remote;

import com.mkrlabs.programmingheroquiz.model.Quiz;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizServices {

    @GET("quiz.json")
    Call<Quiz> getQuiz();

}
