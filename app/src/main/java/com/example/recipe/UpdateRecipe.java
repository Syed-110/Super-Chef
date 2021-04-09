package com.example.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Model.MenuModel;

public class UpdateRecipe extends AppCompatActivity {
    ImageView imageView;
    Uri uri;
    EditText name,prep_time,cook_time,total_time,ing,ins;
    String imageUrl;
    String key,oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String recipeName,recipePrepTime,recipeCookTime,recipeTotalTime,recipeIng,recipeIns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update);
        imageView=findViewById(R.id.iv_foodImage);
        name=findViewById(R.id.text_name);
        prep_time=findViewById(R.id.text_preptime);
        cook_time=findViewById(R.id.text_cooktime);
        total_time=findViewById(R.id.text_totaltime);
        ing=findViewById(R.id.text_ing);
        ins=findViewById(R.id.text_ins);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            Glide.with(getApplicationContext()).load(bundle.getString("imageKey")).into(imageView);
            name.setText(bundle.getString("itemNameKey"));
            prep_time.setText(bundle.getString("prepTimeKey"));
            cook_time.setText(bundle.getString("cookTimeKey"));
            total_time.setText(bundle.getString("totalTimeKey"));
            ing.setText(bundle.getString("ingKey"));
            ins.setText(bundle.getString("insKey"));
            key=bundle.getString("key");
            oldImageUrl=bundle.getString("imageKey");
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Recipe").child(name.getText().toString());
    }

    public void btnSelectImage(View view) {
        Intent photoPicker= new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_FIRST_USER){
            if (data != null) {
                uri=data.getData();
                Log.d("mesage","Uri"+uri);
            }
            imageView.setImageURI(uri);
        }
        else{
            Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnUpdateRecipe(View view) {
        recipeName=name.getText().toString().trim();
        recipePrepTime=prep_time.getText().toString().trim();
        recipeCookTime=cook_time.getText().toString().trim();
        recipeTotalTime=total_time.getText().toString().trim();
        recipeIng=ing.getText().toString().trim();
        recipeIns=ins.getText().toString().trim();

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating Recipe...");
        progressDialog.show();
        storageReference= FirebaseStorage.getInstance().getReference().child("RecipeImage").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uriImage=uriTask.getResult();
                imageUrl=uriImage.toString();
                Log.d("imageurl","URL"+imageUrl);
                uploadRecipe();
                progressDialog.dismiss();
            }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
        public void uploadRecipe(){
        MenuModel menuModel=new MenuModel(
                recipeName,
                recipeCookTime,
                recipePrepTime,
                recipeTotalTime,
                imageUrl,
                recipeIng,
                recipeIns
        );

        databaseReference.setValue(menuModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference reference=FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                reference.delete();
                Toast.makeText(UpdateRecipe.this, "Recipe Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}