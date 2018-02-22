package com.mohbou.quizapplearning.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mohbou.quizapplearning.App;
import com.mohbou.quizapplearning.R;

import com.mohbou.quizapplearning.model.entities.Question;



import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MVPMainActivityInterface.View {

    private static final boolean FORWARD = true;
    private static final boolean BACKWARD = false;

    private TextView questionStatement;
    private Button nextButton;
    private Button previousButton;

    private RecyclerView recyclerView;
    private MainAdapter adapter;

    @Inject
    MVPMainActivityInterface.Presenter presenter;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //prepare Injection
        ((App) getApplication()).getComponent().inject(this);

        //GUI elements initialization
        questionStatement = findViewById(R.id.question_statement);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        nextButton.setOnClickListener(view -> presenter.assertAnswers(adapter.getQuestion(), FORWARD ));

        previousButton.setOnClickListener(view -> presenter.assertAnswers(adapter.getQuestion(), BACKWARD));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.setDisposable(mCompositeDisposable);
        presenter.loadQuiz();//loading questions
    }


    @Override
    public void displayQuestion(Question question) {
        questionStatement.setText(question.getStatement());

        if (adapter == null) {
            adapter = new MainAdapter(question);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setQuestion(question);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void displayNoQuestionAvailable() {
        Toast.makeText(this, R.string.no_question_found, Toast.LENGTH_LONG).show();
        nextButton.setVisibility(View.GONE);
        previousButton.setVisibility(View.GONE);
    }

    @Override
    public void allowNext(boolean next) {
        nextButton.setVisibility(next ? View.VISIBLE : View.GONE);


    }

    @Override
    public void allowPrevious(boolean previous) {
        previousButton.setVisibility(previous ? View.VISIBLE : View.GONE);
    }

    @Override
    public void next() {
        presenter.updateQuestion(adapter.getQuestion());
        presenter.loadQuestion(FORWARD);
    }

    @Override
    public void previous() {
        presenter.updateQuestion(adapter.getQuestion());
        presenter.loadQuestion(BACKWARD);
    }

    @Override
    public void noAnswerSelected() {
        Toast.makeText(this, "No Answer selected!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }
}
