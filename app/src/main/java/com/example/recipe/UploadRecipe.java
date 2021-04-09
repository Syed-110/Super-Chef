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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Model.MenuModel;

public class UploadRecipe extends AppCompatActivity {
    ImageView imageView;
    Uri uri;
    EditText name,prep_time,cook_time,total_time,ing,ins;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload_recipe);
        imageView=findViewById(R.id.iv_foodImage);
        name=findViewById(R.id.text_name);
        prep_time=findViewById(R.id.text_preptime);
        cook_time=findViewById(R.id.text_cooktime);
        total_time=findViewById(R.id.text_totaltime);
        ing=findViewById(R.id.text_ing);
        ins=findViewById(R.id.text_ins);
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
    public void uploadImage(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("RecipeImage").child(uri.getLastPathSegment());
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading Recipe...");
        progressDialog.show();
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
                Toast.makeText(UploadRecipe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void btnUploadImage(View view) {
        String name1=name.getText().toString().trim();
        String cook=cook_time.getText().toString().trim();
        String prep=prep_time.getText().toString().trim();
        String tot=total_time.getText().toString().trim();
        String ingr=ing.getText().toString().trim();
        String inst=ins.getText().toString().trim();
        if(name1.isEmpty()){
            name.setError("Name can't be empty");
        }
        else if(prep.isEmpty()){
            prep_time.setError("Prepare time can't be empty");
        }
        else if(cook.isEmpty()){
            cook_time.setError("Cook time can't be empty");
        }
        else if(tot.isEmpty()){
            total_time.setError("Total time can't be empty");
        }
        else if(ingr.isEmpty()){
            ing.setError("Ingredients can't be empty");
        }
        else if(inst.isEmpty()){
            ins.setError("Instruction can't be empty");
        }
        else{
            uploadImage();
        }
    }
    public void uploadRecipe(){
        MenuModel menuModel=new MenuModel(
                name.getText().toString(),
                cook_time.getText().toString(),
                prep_time.getText().toString(),
                total_time.getText().toString(),
                imageUrl,
                ing.getText().toString(),
                ins.getText().toString()
        );
        FirebaseDatabase.getInstance().getReference("Recipe").child(name.getText().toString()).setValue(menuModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UploadRecipe.this, "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadRecipe.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}