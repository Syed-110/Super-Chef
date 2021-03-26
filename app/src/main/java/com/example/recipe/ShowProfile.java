package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowProfile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    ImageView pic;
    TextView yourname,youremail;
    Button logout,change;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        pic=findViewById(R.id.imageView);
        Intent i=getIntent();
        String f=i.getStringExtra("fullname");
        String e=i.getStringExtra("email");
        yourname=findViewById(R.id.yourname);
        youremail=findViewById(R.id.youremail);
        youremail.setText(e);
        yourname.setText(f);
        logout=findViewById(R.id.logout);
        change=findViewById(R.id.change);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Glide.with(this).load(acct.getPhotoUrl()).into(pic);
            yourname.setText(personName);
            youremail.setText(personEmail);
//            DocumentReference docRef = firebaseFirestore.collection("users").document(firebaseUser.getUid());
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                     public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                          if (task.isSuccessful()) {
//                               DocumentSnapshot document = task.getResult();
//                                    if (document != null) {
//                                         Log.d("LOGGER","name "+document.getString("fullname"));
//                                    } else {
//                                         Log.d("LOGGER", "No such document");
//                                    }
//                               } else {
//                                    Log.d("LOGGER", "get failed with ", task.getException());
//                                }
//                          }
//                     });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                            FirebaseAuth.getInstance().signOut();
                            Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                            //Toast.makeText(getApplicationContext(), "Logout Successfully! From Google", Toast.LENGTH_SHORT).show();
                            Log.d("emailid", "email " + acct.getEmail());
                            Toast.makeText(getApplicationContext(), "Email: " + acct.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(i1);
                        }
                    });
                }
            });
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Profile.class);
                    intent.putExtra("fullname",yourname.getText().toString());
                    intent.putExtra("email",youremail.getText().toString());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connetion Failed", Toast.LENGTH_SHORT).show();
    }
}