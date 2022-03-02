package com.mkrlabs.programmingheroquiz.ui.quiz;

import com.mkrlabs.programmingheroquiz.model.Quiz;

public interface QuizContract {

    interface View{
        void showLoading();
        void hideLoading();
        void quizList(Quiz quiz);
        void showError(String error);
    }
    interface  Model{
        interface ModelResponse{
            void  getQuizResponse();
        }
        void quizList(Quiz quiz);
        void quizResponseError(String error);

    }
    interface Presenter{
        void getQuiz();
        void destroyView();
    }
}
