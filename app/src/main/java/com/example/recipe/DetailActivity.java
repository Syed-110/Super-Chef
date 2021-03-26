package com.example.recipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView prepTime,cookTime,totalTime,ing,ins;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.ivImage);
        prepTime=findViewById(R.id.txtprepTime);
        cookTime=findViewById(R.id.txtcookTime);
        totalTime=findViewById(R.id.txttotalTime);
        ing=findViewById(R.id.txting);
        ins=findViewById(R.id.txtinst);

        prepTime.setVisibility(View.VISIBLE);
        cookTime.setVisibility(View.VISIBLE);
        totalTime.setVisibility(View.VISIBLE);
        ing.setVisibility(View.VISIBLE);
        ins.setVisibility(View.VISIBLE);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            Glide.with(this).load(bundle.getString("Image")).into(imageView);
            if(prepTime!=null){
                prepTime.setText("Prepare Time: "+bundle.getString("PrepTime"));
            }
            else{
                prepTime.setVisibility(View.GONE);
            }
            if(cookTime!=null){
                cookTime.setText("Cook Time: "+bundle.getString("CookTime"));
            }
            else{
                cookTime.setVisibility(View.GONE);
            }
            if(totalTime!=null){
                totalTime.setText("Total Time: "+bundle.getString("TotalTime"));
            }
            else{
                totalTime.setVisibility(View.GONE);
            }
            ing.setText("Ingredients: "+bundle.getString("Ingredients"));
            ins.setText("Instructions: "+bundle.getString("Instruction"));
        }
    }
}