package com.noworri.noworri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import com.noworri.noworri.Adapter.CardListAdapter;
import com.noworri.noworri.Model.CardModel;

public class RegisteredCard extends AppCompatActivity {

    public static String[] nameArray = {"****************5862", "****************5862"};
    public static String[] versionArray = {"Expires 05/19", "Expires 05/19"};
    public static Integer[] drawableArray = {R.drawable.visa, R.drawable.mastercard};
    public static Integer[] id_ = {0, 1};
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<CardModel> data;
    public static View.OnClickListener myOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_card);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();*/

        myOnClickListener = new MyOnClickListener(this);
        recyclerView =  findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        for (int i = 0; i < nameArray.length; i++) {
            data.add(new CardModel(
                    nameArray[i],
                    versionArray[i],
                    id_[i],
                    drawableArray[i]
            ));
        }

        adapter = new CardListAdapter(data);
        recyclerView.setAdapter(adapter);
    }
    private static class MyOnClickListener implements View.OnClickListener {
        Context context;
        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

        }


    }

    public void addCard(View v)
    {
        startActivity(new Intent(RegisteredCard.this, AddCardActivity.class));
    }


}
