package com.example.blogapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blogapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPostActivity extends AppCompatActivity {
ImageView image ;
EditText title ;
EditText describtion ;
Button post ;
Uri imageURI ;

StorageReference mStorage ;
FirebaseDatabase mFirebaseDatabase ;
DatabaseReference mDatabaseReference ;
FirebaseUser mUser ;
FirebaseAuth mAuth ;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mAuth = FirebaseAuth.getInstance() ;
        mUser = mAuth.getCurrentUser() ;
        mFirebaseDatabase = FirebaseDatabase.getInstance() ;
        mDatabaseReference= mFirebaseDatabase.getReference().child("mBlog");
        mStorage = FirebaseStorage.getInstance().getReference();

        image = findViewById(R.id.addImage);
        title = findViewById(R.id.addTitle);
        describtion = findViewById(R.id.addDetails) ;
        post = findViewById(R.id.addPost) ;

        post.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                if(!title.getText().toString().equals("") &&!describtion.getText().toString().equals("")&&imageURI!=null){
                    Toast.makeText(getApplicationContext(), "Posting ...", Toast.LENGTH_SHORT).show();

                    final String titleval =title.getText().toString().trim();
                    final String desval = describtion.getText().toString().trim() ;
                    final StorageReference path = mStorage.child("blog_images").child(imageURI.getLastPathSegment()) ;

                    path.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri uri1 = uri;

                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm" , Locale.getDefault());
                                    String time = simpleDateFormat.format(new Date()) ;
                                        DatabaseReference databaseReference = mDatabaseReference.push();
                                    databaseReference.child("title").setValue(titleval);
                                    databaseReference.child("describtion").setValue(desval);
                                    databaseReference.child("time").setValue(time);
                                    databaseReference.child("image").setValue(uri1.toString());
                                    startActivity(new Intent(getApplicationContext() , PostListActivity.class));
                                    finish();
                                }
                            });
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(), "Enter the Title , image and Description for the post",
                            Toast.LENGTH_SHORT).show();
             }
         });

         image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent (Intent.ACTION_GET_CONTENT) ;
                 intent.setType("image/*") ;
                 startActivityForResult(intent , 1);
             }
         });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            imageURI= data.getData() ;
            image.setImageURI(imageURI);
        }
    }
}
