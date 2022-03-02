package com.mkrlabs.programmingheroquiz.ui.quiz;

import android.util.Log;

import com.mkrlabs.programmingheroquiz.model.Quiz;
import com.mkrlabs.programmingheroquiz.repository.remote.QuizApi;
import com.mkrlabs.programmingheroquiz.repository.remote.QuizServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizModel implements QuizContract.Model.ModelResponse{

    private final QuizServices quizServices ;

    QuizContract.Model model;

    public QuizModel(QuizContract.Model model) {
        this.model = model;
        quizServices = QuizApi.getRetrofitClient().create(QuizServices.class);
    }

    @Override
    public void getQuizResponse() {

        quizServices.getQuiz().enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful() && response !=null){

                    Log.v("Quiz",response.toString());
                    Quiz quiz = response.body();
                    model.quizList(quiz);
                }

            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                String error = t.getMessage();
                Log.v("Quiz",error);
                model.quizResponseError(error);

            }
        });

    }
}
