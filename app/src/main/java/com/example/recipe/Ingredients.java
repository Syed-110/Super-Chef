package com.example.recipe;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Adapter.DatabaseHandler;

public class Ingredients extends AppCompatActivity {
    EditText editText;
    ImageView imgv1;
    Button opt,opt1;
    ImageButton option;
    TextView count;
    FrameLayout fl;
    AppBarLayout appbarlay;
    RelativeLayout rlv,r2v,r3v;
    Toolbar toolbar;
    LinearLayout pantry,addmore;
    SharedPreferences ingredients;
    private  static  final  String sharedprefmsg="mying";
    private DatabaseHandler dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbh=new DatabaseHandler(this);

        appbarlay=findViewById(R.id.appbar);
        toolbar=findViewById(R.id.toolbar);
        fl=findViewById(R.id.flFragment);
        fl.setForeground(null);
        loadfragment(new Pantry());
        rlv=findViewById(R.id.relativecollapse);
        r3v=findViewById(R.id.pantrysecondview);
        pantry=findViewById(R.id.pantrylayout);
        pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,new SelectedItem()).addToBackStack(null).commit();
                r2v.setVisibility(View.INVISIBLE);
                r3v.setVisibility(View.VISIBLE);
            }
        });

        r2v=findViewById(R.id.secondview);
        r2v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("layout","layout clicked");
                r2v.setVisibility(View.INVISIBLE);
            }
        });

        addmore=findViewById(R.id.pantrysecondlayout);
        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,new Pantry()).addToBackStack(null).commit();
                r3v.setVisibility(View.INVISIBLE);
                r2v.setVisibility(View.VISIBLE);
            }
        });

        appbarlay.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    rlv.setVisibility(View.VISIBLE);
                    rlv.setBackgroundColor(Color.parseColor("#FF03DAC5"));
                    toolbar.setBackgroundColor(Color.parseColor("#FF03DAC5"));

                } else {
                    rlv.setVisibility(View.GONE);
                    rlv.setBackgroundColor(0);
                    toolbar.setBackgroundColor(0);
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch(item.getItemId())
                {

                    case R.id.navigation_Menu:
                        fragment=new Menu();
                        r2v.setVisibility(View.INVISIBLE);
                        loadfragment(fragment);
                        break;

                    case R.id.navigation_favourites:
                        fragment=new Favourites();
                        r2v.setVisibility(View.INVISIBLE);
                        loadfragment(fragment);
                        break;

                    case R.id.navigation_shoppinglist:
                        fragment=new Shoppinglist();
                        r2v.setVisibility(View.INVISIBLE);
                        loadfragment(fragment);
                        break;

                    case R.id.navigation_pantry:
                        fragment=new Pantry();
                        r2v.setVisibility(View.VISIBLE);
                        loadfragment(fragment);
                        break;
                }
                return true;
            }
        });
    }
    @SuppressLint("RestrictedApi")
    public void popup(View v){
        @SuppressLint("RestrictedApi") MenuBuilder menuBuilder =new MenuBuilder(getApplicationContext());
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.clear, menuBuilder);
        @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, v);
        optionsMenu.setForceShowIcon(true);
        optionsMenu.show();
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                if(item.getItemId()==R.id.remove_ing){
                    dbh.deleteallingredients();
                    loadfragment(new Pantry());
                    Toast.makeText(Ingredients.this, "All ingredients deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public void onMenuModeChange(@NonNull MenuBuilder menu) {

            }
        });
    }
    private void loadfragment(Fragment frag)
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment,frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}