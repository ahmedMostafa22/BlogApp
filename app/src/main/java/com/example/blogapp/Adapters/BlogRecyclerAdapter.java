package com.example.blogapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogapp.Classes.Blog;
import com.example.blogapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private List<Blog> blogList ;
    private Context  context ;
    public BlogRecyclerAdapter(Context context ,List<Blog> blogList) {
        this.context = context ;
        this.blogList = blogList ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item , parent , false) ;
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Blog blog = blogList.get(position) ;
        holder.title.setText(blog.getTitle());
        holder.describtion.setText(blog.getDescribtion());
        holder.date.setText(blog.getTime());
        String imgURI =blog.getImage() ;
        Picasso.get().load(imgURI).into(holder.pic);


    }

    @Override
    public int getItemCount() {
        return blogList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title ;
        TextView date ;
        TextView describtion ;
        ImageView pic ;
        String userid ;
        public ViewHolder(@NonNull View view , Context ctx) {
            super(view);
            context = ctx ;
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            describtion = view.findViewById(R.id.text);
            pic = view.findViewById(R.id.image);
            userid = null ;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
