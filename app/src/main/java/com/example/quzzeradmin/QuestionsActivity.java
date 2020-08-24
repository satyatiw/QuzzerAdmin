package com.example.quzzeradmin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private List<QuestionModel> list;
    private Dialog loadingDialog;

    RecyclerView recycler_View;
    private Button add_Button;
    private Button excel_Button;
    private QuestionsAdapter adapter;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String categoryName = getIntent().getStringExtra("Category");
        final int set = getIntent().getIntExtra("setNo",1);
        getSupportActionBar().setTitle(categoryName+"/set "+set);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recycler_View = findViewById((R.id.recycler_View));
        add_Button = findViewById(R.id.add_Button);
        excel_Button = findViewById(R.id.excel_Button);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recycler_View.setLayoutManager(layoutManager);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);




        list = new ArrayList<>();

         getData(categoryName,set);

        adapter = new QuestionsAdapter(list);
        recycler_View.setAdapter(adapter);

         //Add Button coding to send data in database.
         add_Button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               Intent addQuestion = new Intent(QuestionsActivity.this, AddQuestionActivity.class);
               addQuestion.putExtra("categoryName",categoryName);
               addQuestion.putExtra("set",set);
               startActivity(addQuestion);
             }
         });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
            return super.onOptionsItemSelected(item);
    }


    //getData Method that is importing data from firebase to our app
    private void getData(String categoryName, final int set)
    {
        loadingDialog.show();

        FirebaseDatabase.getInstance().getReference().child("SETS").child(categoryName)
                .child("questions").orderByChild("setNo")
                .equalTo(set).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    String id = snapshot1.getKey();
                    String question = snapshot1.child("question").getValue().toString();
                    String a = snapshot1.child("optionA").getValue().toString();
                    String b = snapshot1.child("optionB").getValue().toString();
                    String c = snapshot1.child("optionC").getValue().toString();
                    String d = snapshot1.child("optionD").getValue().toString();
                    String answer = snapshot1.child("correctAnswer").getValue().toString();
                    int set = Integer.parseInt(snapshot1.child("setNo").getValue().toString());

                    list.add(new QuestionModel(id,question,a,b,c,d,answer,set));
                }
                loadingDialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });

    }
}