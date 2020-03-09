package com.example.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterationActivity extends AppCompatActivity {

    EditText fn , sn , email , password ;
    Button reg ;
    ImageView imageView ;

    StorageReference firebaseStorage ;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;
    FirebaseAuth firebaseAuth ;

    Uri imageURI ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        fn = findViewById(R.id.fn);
        sn = findViewById(R.id.sn);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.pass);
        reg = findViewById(R.id.reg);
        imageView = findViewById(R.id.pp);

        firebaseStorage=FirebaseStorage.getInstance().getReference() ;
        firebaseAuth = FirebaseAuth.getInstance() ;
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference().child("Useres") ;

        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!fn.getText().toString().isEmpty()&&!sn.getText().toString().isEmpty()&&
                        !email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()&&imageURI!=null) {
                    final StorageReference path = firebaseStorage.child("Profile_Pics").child(imageURI.getLastPathSegment()) ;
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                            addOnCompleteListener(RegisterationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        path.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String userid = firebaseAuth.getCurrentUser().getUid() ;

                                                        DatabaseReference mDatabaseReference = databaseReference.child(userid);
                                                        mDatabaseReference.child("FirstName").setValue(fn.getText().toString());
                                                        mDatabaseReference.child("SecondName").setValue(sn.getText().toString());
                                                        mDatabaseReference.child("Image").setValue(uri.toString());

                                                        startActivity(new Intent(getApplicationContext(),PostListActivity.class));
                                                        finish();
                                                    }
                                                });

                                            }
                                        });


                                    } else
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
                else
                    Toast.makeText(getApplicationContext(),"Please enter your full data", Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
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
            imageView.setImageURI(imageURI);
        }
    }

}
