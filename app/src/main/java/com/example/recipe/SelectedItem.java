package com.example.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.DatabaseHandler;
import Adapter.SelectedPantryItems;

public class SelectedItem extends Fragment implements  DatatransferInterface {
    DatabaseHandler dbh;
    private RecyclerView.Adapter radp;
    Fragmenttoactivity fragact;
    private LinearLayout animatelinearlay;
    Button addingredients;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragact= (Fragmenttoactivity) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement fragment");
        }
    }

    public SelectedItem() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        fragact=null;
        super.onDetach();
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("msg","hello from create view");

        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_selected_item, container, false);
        addingredients=rootview.findViewById(R.id.addingredients);
        animatelinearlay=rootview.findViewById(R.id.nocontlinearlay);
        RecyclerView recycleview=rootview.findViewById(R.id.recyclerview1);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        dbh=new DatabaseHandler(getContext());
        Log.d("mstag","Array is:"+dbh.get_records());
        Log.d("msgtag","length is:"+dbh.get_count_ingredients());
        if(dbh.get_count_ingredients()>0){
            recycleview.setVisibility(View.VISIBLE);
            animatelinearlay.setVisibility(View.GONE);
            radp=new SelectedPantryItems(getActivity(),dbh.get_records(),this);
            recycleview.setAdapter(radp);
        }
        else{
            recycleview.setVisibility(View.GONE);
            animatelinearlay.setVisibility(View.VISIBLE);
        }
        addingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Ingredients.class);
                startActivity(i);
            }
        });
        return rootview;
    }

    @Override
    public void setCount(int count) {
        fragact.communicate(count);
        Log.d("msgtag","count from setcout is:"+count);
    }
}