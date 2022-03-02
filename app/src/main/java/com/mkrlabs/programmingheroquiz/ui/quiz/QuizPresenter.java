package com.mkrlabs.programmingheroquiz.ui.quiz;

import com.mkrlabs.programmingheroquiz.model.Quiz;

public class QuizPresenter implements QuizContract.Presenter ,QuizContract.Model{

    private QuizModel quizModel;
    private QuizContract.View view;

    public QuizPresenter(QuizContract.View view) {
        this.view =view;
        this.quizModel = new QuizModel(this);
    }

    @Override
    public void getQuiz() {
        if (view!=null){
            view.showLoading();
            quizModel.getQuizResponse();
        }
    }

    @Override
    public void destroyView() {
        view =null;
    }

    @Override
    public void quizList(Quiz quiz) {
        if (view!=null){
            view.hideLoading();
            view.quizList(quiz);
        }
    }

    @Override
    public void quizResponseError(String error) {
        if (view!=null){
            view.showError(error);
        }

    }
}
