package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delicious.Adapter.ItemDishAdapter;

public class ListDish extends AppCompatActivity {
    String nameDish = "";
    ItemDishAdapter itemListDishAdapter;
    RecyclerView listDish ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dish);
        Intent intent = getIntent();
        nameDish = intent.getStringExtra("dish");
        Toast.makeText(this, ""+intent.getStringExtra("dish"), Toast.LENGTH_SHORT).show();
        AnhXa();
    }
    public  void AnhXa() {
        listDish = findViewById(R.id.list);

//        itemListDishAdapter = new ItemDishAdapter(getActivity());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        listDish.setLayoutManager(linearLayoutManager);

    }


    //AnhXa

    //getList
}