package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText name,email;
    ImageView pic;
    Button logout,save;
    private GoogleApiClient mGoogleApiClient;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        pic=findViewById(R.id.imageView);
        name=findViewById(R.id.nametext);
        email=findViewById(R.id.emailtext);
        logout=findViewById(R.id.logout);
        save=findViewById(R.id.save);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            Intent data=getIntent();
            String fullname=data.getStringExtra("fullname");
            String Profile_email=data.getStringExtra("email");
            name.setText(fullname);
            email.setText(Profile_email);
            Glide.with(this).load(acct.getPhotoUrl()).into(pic);
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
                            startActivity(i1);
                        }
                    });
                }
            });
            if(name!=null){
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(name.getText().toString().isEmpty() && email.getText().toString().isEmpty()){
                            Toast.makeText(Profile.this, "Name and Email can't be Empty", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            final String pro_email = email.getText().toString();
                            firebaseUser.updateEmail(pro_email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
                                    Map<String, Object> edited = new HashMap<>();
                                    edited.put("email", pro_email);
                                    edited.put("fullname", fullname);
                                    documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(),ShowProfile.class);
                                            intent.putExtra("fullname",fullname);
                                            intent.putExtra("email",pro_email);
                                            finish();
                                        }
                                    });
                                    Toast.makeText(Profile.this, "Profile Changed", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }
}