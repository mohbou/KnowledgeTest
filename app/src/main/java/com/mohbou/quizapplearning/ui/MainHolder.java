package com.mohbou.quizapplearning.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;


public class MainHolder extends RecyclerView.ViewHolder {

    public final CheckedTextView textView;

    public MainHolder(View itemView) {
        super(itemView);
        textView = (CheckedTextView) itemView;
    }
}
