package com.example.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name,prepTime,cookTime,totalTime,ing,ins;
    String imageUrl="",name1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.ivImage);
        name=findViewById(R.id.txtname);
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
            imageUrl=bundle.getString("Image");
            name1=bundle.getString("name");
            Glide.with(this).load(bundle.getString("Image")).into(imageView);
            if(name!=null){
                name.setText("Name: "+bundle.getString("name"));
            }
            else{
                name.setVisibility(View.GONE);
            }
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

    public void btnUpdateRecipe(View view) {
        startActivity(new Intent(getApplicationContext(),UpdateRecipe.class)
                .putExtra("itemNameKey",name1)
                .putExtra("prepTimeKey",prepTime.getText().toString())
                .putExtra("cookTimeKey",cookTime.getText().toString())
                .putExtra("totalTimeKey",totalTime.getText().toString())
                .putExtra("ingKey",ing.getText().toString())
                .putExtra("insKey",ins.getText().toString())
                .putExtra("imageKey",imageUrl)
        );
    }
}