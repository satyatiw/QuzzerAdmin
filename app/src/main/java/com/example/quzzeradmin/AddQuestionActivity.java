package com.example.quzzeradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {
//gaurav  123
    private EditText question;
    private RadioGroup options;
    private LinearLayout answer;
    private Button uploadBtn;
    private String categoryName;
    private int setNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        categoryName = getIntent().getStringExtra("categoryName");
        setNo = getIntent().getIntExtra("setNo", -1);
        if(setNo==-1)
        {
            finish();
            return;
        }


    }

    private void upload()
    {
//        FirebaseDatabase.getInstance().getReference().child("SETS").child(categoryName).child("questions").
    }
}