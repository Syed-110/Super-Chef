package com.example.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Adapter.DatabaseHandler;

public class Ingredients extends AppCompatActivity implements Fragmenttoactivity, fragtoactivityshoppinglist, GoogleApiClient.OnConnectionFailedListener {
    TextView counttv,butcounttv,ing_search,counttexttv,mying,search;
    FrameLayout fl;
    Button backbtn,col_setting,ing_setting,ing_col_user;
    AppBarLayout appbarlay;
    EditText search_icon;
    int flag=0;
    RelativeLayout rlv,pantryfragrellay;
    Toolbar toolbar;
    NestedScrollView nsv;
    CollapsingToolbarLayout collapsetoolbar;
    private DatabaseHandler dbh;
    TextView tv1,tv2,seerecipe;
    LinearLayout linearlay;
    SharedPreferences shpmsgvar,shpmsgret;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    int count=0;
    public Bundle bundle = new Bundle();

    private  static  final  String sharedprefmsg="myprefsfile";
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStart() {
        super.onStart();
    }


    @SuppressLint({"CutPasteId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        nsv=findViewById(R.id.nestedScrollView);
        counttv=findViewById(R.id.counttv);
        collapsetoolbar=findViewById(R.id.collapse);
        backbtn=findViewById(R.id.ing_col_user_pantry);
        pantryfragrellay=findViewById(R.id.pantry_frag_lay);
        search_icon=findViewById(R.id.ing_col_search);
        butcounttv=findViewById(R.id.mypantrycount);
        dbh=new DatabaseHandler(getApplicationContext());
        linearlay=findViewById(R.id.bottommenulinear);
        appbarlay=findViewById(R.id.appbar);
        toolbar=findViewById(R.id.toolbar);
        fl=findViewById(R.id.flFragment);
        counttexttv=findViewById(R.id.counttv_pantry);
        fl.setForeground(null);
        loadfragment(new Pantry());
        ing_search=findViewById(R.id.ing_col_search);
        shpmsgvar=getSharedPreferences(sharedprefmsg,0);
        SharedPreferences.Editor editor=shpmsgvar.edit();
        editor.putString("message","pantry");
        editor.commit();
        rlv=findViewById(R.id.relativecollapse);
        tv1=findViewById(R.id.mypantrytext);
        tv2=findViewById(R.id.mypantrycount);
        seerecipe=findViewById(R.id.seerecipe);


        ing_search=findViewById(R.id.ing_col_search);
        col_setting=findViewById(R.id.ing_col_settings);
        ing_setting=findViewById(R.id.ing_settings);
        mying=findViewById(R.id.mying);
        search=findViewById(R.id.ing_search);
        ing_col_user=findViewById(R.id.ing_col_user);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        ing_col_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()){
                        firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            if (firebaseUser.isAnonymous()) {
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(getApplicationContext(), "Logout Successfully! From Anonymous", Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            }
                            else if (account != null) {
                                Intent i1 = new Intent(getApplicationContext(), ShowProfile.class);
                                startActivity(i1);
//                                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
//                                    @Override
//                                    public void onResult(Status status) {
//                                        FirebaseAuth.getInstance().signOut();
//                                        Intent i1 = new Intent(getApplicationContext(), Profile.class);
//                                            //Toast.makeText(getApplicationContext(), "Logout Successfully! From Google", Toast.LENGTH_SHORT).show();
//                                        Log.d("emailid", "email" + account.getEmail());
//                                        Toast.makeText(getApplicationContext(), "Email" + account.getEmail(), Toast.LENGTH_SHORT).show();
//                                        startActivity(i1);
//                                    }
//                                });
                            }
                        }
                    }
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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                        search_icon.setOnTouchListener(new View.OnTouchListener() {
                            String name="",img="";
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final int DRAWABLE_LEFT = 0;
                                final int DRAWABLE_TOP = 1;
                                final int DRAWABLE_RIGHT = 2;
                                final int DRAWABLE_BOTTOM = 3;

                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                    if(event.getRawX() >= (search_icon.getRight() - search_icon.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                        Toast.makeText(Ingredients.this, "Search Icon Clicked", Toast.LENGTH_SHORT).show();
                                        String edit=search_icon.getText().toString();
                                        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("Recipe");
                                        databaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot childSnapshot:snapshot.getChildren()) {
                                                    String recipe_name= childSnapshot.getKey();
                                                   // Log.d("recipename","name"+recipe_name);
                                                    Toast.makeText(Ingredients.this, "name"+recipe_name, Toast.LENGTH_SHORT).show();
                                                    //Log.d("msg","EditText "+edit);
                                                    //Log.d("recipekey","name"+bundle.getString("recipe"));
//                                                    String ingredients=childSnapshot.child("ingredients").getValue().toString();
//                                                    String instruction=childSnapshot.child("instruction").getValue().toString();
//                                                    String prep_time=childSnapshot.child("prepare time").getValue().toString();
                                                    String image=childSnapshot.child("image url").getValue().toString();
                                                    if(recipe_name.startsWith(edit)==true || recipe_name.endsWith("\uf8ff"))
                                                    {
                                                        Log.d("recipename","name"+recipe_name);
                                                        name=name+"\n"+recipe_name;
                                                        img=image;
                                                        Bundle bundle = new Bundle();

                                                        bundle.putString("recipe", name);
                                                        bundle.putString("image",img);
                                                        Menu fragInfo = new Menu();
                                                        fragInfo.setArguments(bundle);
                                                        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                                                        transaction.replace(R.id.flFragment, fragInfo);
                                                        transaction.commit();
                                                        if(childSnapshot.child("total time").getValue()!=null)
                                                        {
                                                            String total_time=childSnapshot.child("total time").getValue().toString();
                                                            //Log.d("msg","Recipe Name"+recipe_name+"Ingredients"+ingredients+"Instruction"+instruction+"Prepare Time"+prep_time+"Cook Time"+"\nTotal Time:"+total_time+cook_time+"Image URL"+image);
                                                            //tx1.setText("Recipe Name: "+recipe_name+"\nIngredients: "+ingredients+"\nInstruction: "+instruction+"\nPrepare Time: "+prep_time+"\nCook Time: "+cook_time+"\nTotal Time:"+total_time+"\nImage URL: "+image);
                                                        }
                                                        if(childSnapshot.child("cook time").getValue()!=null)
                                                        {
                                                            String cook_time=childSnapshot.child("cook time").getValue().toString();
                                                            //Log.d("msg","Recipe Name"+recipe_name+"Ingredients"+ingredients+"Instruction"+instruction+"Prepare Time"+prep_time+"Cook Time"+"\nTotal Time:"+total_time+cook_time+"Image URL"+image);
                                                            //tx1.setText("Recipe Name: "+recipe_name+"\nIngredients: "+ingredients+"\nInstruction: "+instruction+"\nPrepare Time: "+prep_time+"\nCook Time: "+cook_time+"\nTotal Time:"+total_time+"\nImage URL: "+image);
                                                        }
                                                    }
                                                    else{
                                                        //Log.d("msg","False value");
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(Ingredients.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                                return false;
                            }
                        });
                        flag=0;
                        col_setting.setVisibility(View.GONE);
                        pantryfragrellay.setVisibility(View.GONE);
                        collapsetoolbar.setVisibility(View.GONE);
                        ing_setting.setVisibility(View.GONE);
                        mying.setText("Super Cook");
                        ing_search.setHint("Search for anything");
                        search.setHint("Search for anything");
                        toolbar.setVisibility(View.VISIBLE);
                        nsv.setNestedScrollingEnabled(true);
                        counttv.setVisibility(View.GONE);
                        fragment=new Menu();
                        loadfragment(fragment);
                        linearlay.setVisibility(View.GONE);
                        break;

                    case R.id.navigation_favourites:
                        flag=0;
                        ing_search.setHint("Find...");
                        pantryfragrellay.setVisibility(View.GONE);
                        col_setting.setVisibility(View.GONE);
                        collapsetoolbar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.GONE);
                        counttv.setVisibility(View.VISIBLE);
                        mying.setText("Favourites");
                        nsv.setNestedScrollingEnabled(false);
                        counttv.setText("You have 0 Favourites");
                        counttexttv.setText("You have 0 Favourites");
                        fragment=new Favourites();
                        loadfragment(fragment);
                        linearlay.setVisibility(View.GONE);
                        break;

                    case R.id.navigation_shoppinglist:
                        flag=1;
                        ing_search.setHint("Add something");
                        col_setting.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.GONE);
                        counttv.setVisibility(View.VISIBLE);
                        mying.setText("Shopping List");
                        counttexttv.setText("You have "+dbh.get_count_ingredients_cart()+" items in the list");
                        fragment=new Shoppinglist();
                        collapsetoolbar.setVisibility(View.VISIBLE);
                        pantryfragrellay.setVisibility(View.GONE);
                        tv2.setVisibility(View.VISIBLE);
                        Log.d("count","count is:"+count);
                        counttv.setText("You have "+dbh.get_count_ingredients_cart()+" items in the list");
                        loadfragment(fragment);
                        linearlay.setVisibility(View.GONE);
                        break;

                    case R.id.navigation_pantry:
                        flag=0;
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            checkfrag(getWindow().getDecorView().getRootView());
                        }
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            checkPantry(getWindow().getDecorView().getRootView());
                        }
                        ing_search.setHint("Add/remove/paste ingredients");
                        search.setHint("Add/remove/paste ingredients");
                        collapsetoolbar.setVisibility(View.VISIBLE);
                        col_setting.setVisibility(View.VISIBLE);
                        ing_setting.setVisibility(View.VISIBLE);
                        counttv.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        mying.setText("My Ingredients");
                        nsv.setNestedScrollingEnabled(true);
                        fragment=new Pantry();
                        loadfragment(fragment);
                        linearlay.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        shpmsgret=getSharedPreferences(sharedprefmsg,0);
        if(shpmsgret.contains("message")) {
            String message = shpmsgret.getString("message", "notfound");
            if(message.equals("pantry")){
                finishAffinity();
            }
            else{
                finish();
            }
        }


    }

    @SuppressLint("RestrictedApi")
    public void popup(View v){
        @SuppressLint("RestrictedApi") MenuBuilder menuBuilder =new MenuBuilder(getApplicationContext());
        MenuInflater inflater = new MenuInflater(this);
        if(flag ==0){
            inflater.inflate(R.menu.clear, menuBuilder);
        }
        else{
            inflater.inflate(R.menu.shopping_clear_popup,menuBuilder);
        }

        @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, v);
        optionsMenu.setForceShowIcon(true);
        optionsMenu.show();
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                if(item.getItemId()==R.id.remove_ing){
                    if(dbh.get_count_ingredients()>0){
                        dbh.deleteallingredients();
                        butcounttv.setText(String.valueOf(dbh.get_count_ingredients()));
                        counttv.setText("You have "+dbh.get_count_ingredients()+" ingredients");
                        counttexttv.setText("You have "+dbh.get_count_ingredients()+" ingredients");
                        Toast.makeText(Ingredients.this, "All ingredients deleted", Toast.LENGTH_SHORT).show();
                        shpmsgret=getSharedPreferences(sharedprefmsg,0);
                        Log.d("msgtag","message is:"+shpmsgret.contains("message"));
                        if(shpmsgret.contains("message"))
                        {
                            String message=shpmsgret.getString("message","notfound");
                            Log.d("msgtag",message);
                            Log.d("msg", String.valueOf(message=="pantry"));
                            if(message.equals("pantry")){
                                loadfragment(new Pantry());
                                mying.setText("My Ingredients");
                                tv1.setText("My Pantry");
                                collapsetoolbar.setVisibility(View.VISIBLE);
                                pantryfragrellay.setVisibility(View.GONE);
                                tv2.setVisibility(View.VISIBLE);
                            }
                            else if(message.equals("selectedingredients")){
                                loadfragment(new SelectedItem());
                                collapsetoolbar.setVisibility(View.GONE);
                                pantryfragrellay.setVisibility(View.VISIBLE);
                                tv1.setText("+ Add More");
                                tv2.setVisibility(View.GONE);
                            }
                        }
                    }
                }
                else if(item.getItemId()==R.id.clear_completed){
                    dbh.delete_purchased();
                    counttv.setText("You have "+dbh.get_count_ingredients_cart()+" items in the list");
                    loadfragment(new Shoppinglist());
                }
                else if(item.getItemId() == R.id.clear_all){
                    dbh.empty_cart();
                    counttv.setText("You have "+dbh.get_count_ingredients_cart()+" items in the list");
                    loadfragment(new Shoppinglist());
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



    public void checkfrag(View v){
        shpmsgret=getSharedPreferences(sharedprefmsg,0);
        if(shpmsgret.contains("message"))
        {
            String message=shpmsgret.getString("message","notfound");
            if(message.equals("pantry")){
                SharedPreferences.Editor editor=shpmsgvar.edit();
                editor.putString("message","selectedingredients");
                loadfragment(new SelectedItem());
                collapsetoolbar.setVisibility(View.GONE);
                pantryfragrellay.setVisibility(View.VISIBLE);
                tv1.setText("+ Add More");
                tv2.setVisibility(View.GONE);
                editor.commit();
            }
            else if(message.equals("selectedingredients")){
                SharedPreferences.Editor editor=shpmsgvar.edit();
                editor.putString("message","pantry");
                mying.setText("My Ingredients");
                loadfragment(new Pantry());
                collapsetoolbar.setVisibility(View.VISIBLE);
                pantryfragrellay.setVisibility(View.GONE);
                tv1.setText("My Pantry");
                tv2.setVisibility(View.VISIBLE);
                editor.commit();
            }
        }
    }

    public void checkPantry(View v){
        shpmsgret=getSharedPreferences(sharedprefmsg,0);
        if(shpmsgret.contains("message"))
        {
            String message=shpmsgret.getString("message","notfound");
            Log.d("msgtag",message);
            Log.d("msg", String.valueOf(message=="pantry"));
            if(message.equals("selectedingredients")){
                Log.d("msgtag",message);
                SharedPreferences.Editor editor=shpmsgvar.edit();
                editor.putString("message","pantry");
                mying.setText("My Ingredients");
                loadfragment(new Pantry());
                collapsetoolbar.setVisibility(View.VISIBLE);
                pantryfragrellay.setVisibility(View.GONE);
                tv1.setText("My Pantry");
                appbarlay.setNestedScrollingEnabled(true);
                seerecipe.setBackgroundColor(Color.parseColor("#93C75B"));
                tv2.setVisibility(View.VISIBLE);
                editor.commit();
            }
        }
    }

    @Override
    public void communicate(int count) {
        butcounttv.setText(String.valueOf(count));
        counttexttv.setText("You have "+count+" ingredients");
        counttv.setText("You have "+count+" ingredients");
    }

    @Override
    public void communicate_shoppinglist(int count) {
        this.count=count;
        counttv.setText("You have "+count+" items in the list");
        Log.d("count","count from ingredients  is:"+this.count);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }
}