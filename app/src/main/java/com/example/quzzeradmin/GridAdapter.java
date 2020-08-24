package com.example.quzzeradmin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    public int sets=0;
    private String Category;
    private GridListener listener;

    public GridAdapter(int sets, String Category, GridListener listener)
    {
       this.sets = sets;
       this.listener = listener;
       this.Category = Category;
    }

    @Override
    public int getCount() {
        return sets +1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View counterView, final ViewGroup parent) {
        View view;
        if(counterView == null )
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }
        else
        {
            view = counterView;
        }
        if(position == 0)
        {
            ((TextView)view.findViewById(R.id.textView)).setText("+");
        }else{
            ((TextView)view.findViewById(R.id.textView)).setText(String.valueOf(position));
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0)
                {
                    //Code for Adding questions(Sets)
                    listener.addSet();
                }else{
                        Intent questionIntent = new Intent(parent.getContext(),QuestionsActivity.class);
                        questionIntent.putExtra("Category", Category);
                       questionIntent.putExtra("setNo", position);
                        parent.getContext().startActivity(questionIntent);
                }
             }
        });
        return view;
    }
    public interface GridListener{
        public void addSet();
    }

}
