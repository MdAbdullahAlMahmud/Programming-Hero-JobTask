package com.mkrlabs.programmingheroquiz.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mkrlabs.programmingheroquiz.R;
import com.mkrlabs.programmingheroquiz.model.Quiz;
import com.mkrlabs.programmingheroquiz.repository.shred_pref.SharedPref;
import com.mkrlabs.programmingheroquiz.ui.main_menu.MainMenuActivity;

public class QuizActivity extends AppCompatActivity implements QuizContract.View{

    private QuizPresenter presenter;
    private ProgressBar quizProgressBar;
    private LinearLayout container;
    private Button quizNextButton;
    private TextView qsTV;
    private int count;
    private int score = 0;
    private TextView qsCountTV,questionScoreTV,totalScoreTV;
    private int position=0;
    private ImageView questionImage;
    private SharedPref sharedPref;
    private CardView questionHeaderHolderCardView;
    private LinearLayout questionOptionsLinearLayout;
    private Toolbar quizToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initialize();
        sharedPref = new SharedPref(this);
        presenter = new QuizPresenter(this);
        quizNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQuestion();
            }
        });


    }

    private void generateQuestion(){
        quizNextButton.setEnabled(false);
        quizNextButton.setAlpha(.5f);
        enabledOption(true);
        qsCountTV.setText(questionCountPreviewText(position+1,quiz.getQuestions().size()));
        position++;
        if (position<quiz.getQuestions().size()){
            count=0;
            setQuestionWithAnimation(qsTV,0,quiz.getQuestions().get(position).getQuestion());
            questionScoreTV.setText(quiz.getQuestions().get(position).getScore().toString() +" Point");

            if (quiz.getQuestions().get(position).getQuestionImageUrl()!=null &&
            !quiz.getQuestions().get(position).getQuestionImageUrl().equals("null")
            ){
                questionImage.setVisibility(View.VISIBLE);
                loadImageIfExists(quiz.getQuestions().get(position).getQuestionImageUrl());
            }else {
                questionImage.setVisibility(View.GONE);
                questionImage.setImageResource(R.drawable.programming_hero_logo);
            }

        }else {

            if (score>sharedPref.getHighScore()){
                sharedPref.setHighScore(score);
                Toast.makeText(QuizActivity.this, "New High Score", Toast.LENGTH_SHORT).show();
            }
            enabledOption(false);
            Intent intent = new Intent(QuizActivity.this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    private void initialize() {
        quizProgressBar = findViewById(R.id.quizProgressBar);
        container=findViewById(R.id.optionContainer);
        qsTV=findViewById(R.id.questionTV);
        quizNextButton=findViewById(R.id.quizNextButton);
        qsCountTV=findViewById(R.id.qsCountTV);
        questionScoreTV=findViewById(R.id.questionScoreTV);
        questionImage=findViewById(R.id.questionImage);
        totalScoreTV=findViewById(R.id.totalScoreTV);
        questionHeaderHolderCardView = findViewById(R.id.questionHeaderHolderCardView);
        questionOptionsLinearLayout = findViewById(R.id.questionOptionsLinearLayout);
        quizToolbar = findViewById(R.id.quizToolbar);
    }

    private void setOnOptionsButtonClickListener() {

        for (int i=0;i<4;i++){
            int index = i;
            container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((AppCompatButton) view , index);
                }
            });
        }
    }
    private void checkAnswer(AppCompatButton selectedButton,int index) {
        enabledOption(false);

        String selectedOption;
        switch (index){
            case 0:
                selectedOption = "A";
                break;
            case 1:
                selectedOption = "B";
                break;
            case 2:
                selectedOption = "C";
                break;
            case 3:
                selectedOption = "D";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }

        if (selectedOption.equals(quiz.getQuestions().get(position).getCorrectAnswer())){
            score= score+quiz.getQuestions().get(position).getScore();
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }else {
            //wrong ans
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            AppCompatButton correctAnsButton = (AppCompatButton) container.findViewWithTag(getTagFromCorrectOption(position,quiz.getQuestions().get(position).getCorrectAnswer()));
            correctAnsButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
        totalScoreTV.setText("Score: "+score);

        quizNextButton.setEnabled(true);
        quizNextButton.setAlpha(1f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                generateQuestion();
            }
        },3000);
    }

    private String getTagFromCorrectOption(int position,String ans){
        String tag = null;
        if (ans.equals("A")){
            tag = quiz.getQuestions().get(position).getAnswers().getA();
        } else if (ans.equals("B")) {
            tag = quiz.getQuestions().get(position).getAnswers().getB();
        }
        else if (ans.equals("C")) {
            tag = quiz.getQuestions().get(position).getAnswers().getC();
        }
        else if (ans.equals("D")) {
            tag = quiz.getQuestions().get(position).getAnswers().getD();
        }
        return tag; 
        
    }
    private void enabledOption(boolean status){
        for (int i=0;i<4;i++){
            container.getChildAt(i).setEnabled(status);
            if(status){
                container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
        }
    }

    @Override
    public void showLoading() {
        questionHeaderHolderCardView.setVisibility(View.INVISIBLE);
        questionOptionsLinearLayout.setVisibility(View.INVISIBLE);
        quizToolbar.setVisibility(View.INVISIBLE);

        quizProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        questionHeaderHolderCardView.setVisibility(View.VISIBLE);
        questionOptionsLinearLayout.setVisibility(View.VISIBLE);
        quizToolbar.setVisibility(View.VISIBLE);
        quizProgressBar.setVisibility(View.GONE);

    }

    private Quiz quiz;
    @Override
    public void quizList(Quiz quizItem) {
        this.quiz = quizItem;
        qsCountTV.setText(questionCountPreviewText(position,quizItem.getQuestions().size()));
        setQuestionWithAnimation(qsTV,0,quiz.getQuestions().get(position).getQuestion());
        questionScoreTV.setText(quiz.getQuestions().get(position).getScore().toString() +" Point");
        setOnOptionsButtonClickListener();
    }

    private String questionCountPreviewText(int position, int size){
        String pattern = "Question: " + (position+1)+"/"+size;
        return pattern;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(QuizActivity.this, "Error "+ error, Toast.LENGTH_SHORT).show();

    }
    void setQuestionWithAnimation(View view , int value, String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                totalScoreTV.setText("Score: "+score);
                if(value==0 && count<4){
                    String option ="";
                    if(count==0){
                        option=quiz.getQuestions().get(position).getAnswers().getA();

                    }else if(count==1){
                        option=quiz.getQuestions().get(position).getAnswers().getB();
                    }else if(count==2){

                        if (quiz.getQuestions().get(position).getAnswers().getC()==null){
                            container.getChildAt(count).setVisibility(View.GONE);
                        }else {
                            container.getChildAt(count).setVisibility(View.VISIBLE);
                        }
                        option=quiz.getQuestions().get(position).getAnswers().getC();

                    } else if (count==3) {
                        if (quiz.getQuestions().get(position).getAnswers().getD()==null){
                            container.getChildAt(count).setVisibility(View.GONE);
                        }else {
                            container.getChildAt(count).setVisibility(View.VISIBLE);
                        }

                        option=quiz.getQuestions().get(position).getAnswers().getD();
                    }
                    setQuestionWithAnimation(container.getChildAt(count),0,option);
                    count++;

                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (value==0){
                    try {
                        ((TextView)view).setText(data);
                    }catch (Exception e){
                        ((AppCompatButton)view).setText(data);
                    }
                    view.setTag(data);
                    setQuestionWithAnimation(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
    }

    private void loadImageIfExists(String questionImageUrl) {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(this)
                .load(questionImageUrl)
                .error(R.drawable.programming_hero_logo)
                .placeholder(circularProgressDrawable)
                .into(questionImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getQuiz();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}