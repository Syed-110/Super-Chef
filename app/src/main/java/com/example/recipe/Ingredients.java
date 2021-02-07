package com.example.recipe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Adapter.DatabaseHandler;

public class Ingredients extends AppCompatActivity {
    EditText editText;
    ImageView imgv1;
    Button opt;
    ImageButton option;
    TextView tx1;
    FrameLayout fl;
    AppBarLayout appbarlay;
    RelativeLayout rlv;
    Toolbar toolbar;
    private DatabaseHandler dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbh=new DatabaseHandler(this);
        opt=findViewById(R.id.ing_col_settings);
        appbarlay=findViewById(R.id.appbar);
        toolbar=findViewById(R.id.toolbar);
        fl=findViewById(R.id.flFragment);
        fl.setForeground(null);
        loadfragment(new Pantry());
        rlv=findViewById(R.id.relativecollapse);
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
                        loadfragment(fragment);
                        break;

                    case R.id.navigation_favourites:

                        fragment=new Favourites();
                        loadfragment(fragment);
                        break;

                    case R.id.navigation_shoppinglist:
                        fragment=new Shoppinglist();
                        loadfragment(fragment);
                        break;

                    default:
                        fragment=new Pantry();
                        loadfragment(fragment);
                        break;
                }

                return true;
            }
        });
        opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.clear, popup.getMenu());
                popup.show();
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