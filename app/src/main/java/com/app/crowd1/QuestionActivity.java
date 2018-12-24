package com.app.crowd1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ListIterator;

import back.Answer;
import back.Game;
import back.Profil;
import back.Question;
import gui.AnswerSetter;

public class QuestionActivity extends AppCompatActivity {

    public Game game;
    public Profil profil;
    public Question question;
    public ProgressBar progress;
//    public ListIterator<Question> questionIterator;
//    public ListIterator<Answer> answerIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        Intent inetnt = getIntent();
        this.game = (Game)inetnt.getSerializableExtra("game");
        this.profil = (Profil) inetnt.getSerializableExtra("profil");

        this.question = game.getQuestions().get(game.getIndex());
        game.nextIndex();
        this.progress = findViewById(R.id.progress);

//        ArrayList<?> games = (ArrayList<?>) thisIntent.getSerializableExtra("games");


//        Intent inetnt = getIntent();
//        this.game = (Game)inetnt.getSerializableExtra("game");
//        this.questionIterator = game.getQuestions().listIterator();
//        this.question = questionIterator.next();
//        this.answerIterator = question.getAnswers().listIterator();
//
//        LinearLayout questionlayout = (LinearLayout)findViewById(R.id.questionlayout);

        LinearLayout answerLayout = (LinearLayout)findViewById(R.id.answerlayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
        TextView questionText = (TextView) findViewById(R.id.question);
        questionText.setText(question.getQuestion());
//        questionlayout.addView(questionText, lp);

//        while(question.getAnswers().isEmpty()){
//            try {
//                wait(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        AnswerSetter answerSetter = new AnswerSetter(this, question, profil.connector, progress, lp, answerLayout, game, profil);
        answerSetter.execute("");

//        for(final Answer x : question.getAnswers()){
////        for(int i = 0; i < 4; i++){
//            final ToggleButton answer = new ToggleButton(this);
////            answer.setText(question.getAnswers().get(question.getIndex()).getAnswer());
//            answer.setText(x.getAnswer());
//            answer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        answer.setText("Your choice");
//                    } else {
//                        answer.setText(x.getAnswer());
//                    }
//                }
//            });
////            question.nextIndex();
//            answerLayout.addView(answer, lp);
//        }


//        while(answerIterator.hasNext()){
//            ToggleButton answer = new ToggleButton(this);
//            answer.setText(answerIterator.next().getAnswer());
//            answerLayout.addView(answer, lp);
//        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("profil", profil);
        intent.putExtra("game", game);
        this.startActivity(intent);
    }
}
