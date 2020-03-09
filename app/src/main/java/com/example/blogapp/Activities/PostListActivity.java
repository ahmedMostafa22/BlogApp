package com.example.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.blogapp.Classes.Blog;
import com.example.blogapp.Adapters.BlogRecyclerAdapter;
import com.example.blogapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostListActivity extends AppCompatActivity {
 FirebaseAuth mAuth ;
 FirebaseUser mUser ;
 FirebaseDatabase mDatabase ;
 DatabaseReference mDatabaseReference;

 List<Blog> blogList ;
 BlogRecyclerAdapter blogRecyclerAdapter ;
 RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        recyclerView = findViewById(R.id.recylerview);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance() ;
        mDatabaseReference = mDatabase.getReference().child("mBlog") ;
        mDatabaseReference.keepSynced(true);

        blogList= new ArrayList<>() ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Blog blog = dataSnapshot.getValue(Blog.class);
                    blogList.add(blog);
                blogRecyclerAdapter = new BlogRecyclerAdapter(getApplicationContext() , blogList) ;
                recyclerView.setAdapter(blogRecyclerAdapter);
                blogRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.signout :
                if(mUser!=null&&mAuth!=null) {
                    mAuth.signOut();
                    Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.add :
                if(mUser!=null&&mAuth!=null) {
                    Intent intent = new Intent(getApplicationContext(), AddPostActivity.class);
                    startActivity(intent);

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu , menu);
            return super.onCreateOptionsMenu(menu);
        }
}
