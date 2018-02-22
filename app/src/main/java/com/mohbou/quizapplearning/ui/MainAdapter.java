package com.mohbou.quizapplearning.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mohbou.quizapplearning.R;
import com.mohbou.quizapplearning.model.entities.Answer;
import com.mohbou.quizapplearning.model.entities.Question;


public class MainAdapter extends RecyclerView.Adapter<MainHolder> {

    private Question question;

    public MainAdapter(Question question) {
        this.question = question;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_plausible_answer, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainHolder holder, final int position) {
        final Answer answer = question.getAnswers().get(position);
        holder.textView.setText(answer.getStatement());
        holder.textView.setChecked(answer.isSelected());
        holder.textView.setCheckMarkDrawable(answer.isSelected() ? R.drawable.ic_checked : R.drawable.ic_unchecked);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.textView.isChecked()) {
                holder.textView.setCheckMarkDrawable(R.drawable.ic_unchecked);
                    holder.textView.setChecked(false);
                    answer.setSelected(false);
                    question.getAnswers().set(position,answer);
                        }
                else {
                    holder.textView.setCheckMarkDrawable(R.drawable.ic_checked);
                    holder.textView.setChecked(true);
                    answer.setSelected(true);
                    question.getAnswers().set(position,answer);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return question.getAnswers().size();
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
