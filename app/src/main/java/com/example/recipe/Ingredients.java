package com.example.recipe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ingredients extends AppCompatActivity {
    EditText editText;
    ImageView imgv1;
    Button logoutbtn;
    ImageButton search_btn;
    TextView tx1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,new Pantry()).commit();
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);;
        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment=null;
            switch(item.getItemId())
            {
                case R.id.navigation_Menu:
                    fragment=new Menu();
                    break;

                case R.id.navigation_favourites:
                    fragment=new Favourites();
                    break;

                case R.id.navigation_shoppinglist:
                    fragment=new Shoppinglist();
                    break;
                default:
                    fragment=new Pantry();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            return true;
        });
    }
}