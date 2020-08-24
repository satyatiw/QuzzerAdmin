package com.example.quzzeradmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.Viewholder> {

    private List<QuestionModel> questionModelList;

    public QuestionsAdapter(){
    }

    public QuestionsAdapter(List<QuestionModel> questionModelList) {
        this.questionModelList = questionModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent,false);
       return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String question= questionModelList.get(position).getQuestion();
        String answer= questionModelList.get(position).getAnswer();

        holder.setData(question, answer, position);
    }

    @Override
    public int getItemCount() {
        return questionModelList.size() ;
    }



    class Viewholder extends RecyclerView.ViewHolder {
                    private TextView question, answer;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }


        private void setData(String question,String answer,int position)
        {
            this.question.setText(position+1+". "+question);
            this.answer.setText("Ans. "+answer);
        }
    }
}
