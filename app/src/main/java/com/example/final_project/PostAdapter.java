package com.example.final_project;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.example.final_project.Model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private Context mCtx;
    private List<Post> mPosts;

    public PostAdapter(Context mCtx, List<Post> productList) {
        this.mCtx = mCtx;
        this.mPosts = productList;
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.post_card_layout, null);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = mPosts.get(position);

        //binding the data with the viewholder views
        holder.textViewPostContent.setText(post.getPostContent());
        holder.textViewUserName.setText(post.getUserName());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(post.getImage()));
        holder.videoWeb.loadData( mPosts.get(position).getVideoUrl(), "text/html" , "utf-8" );

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        TextView textViewPostContent;
        ImageView imageView;
        TextView textViewUserName;
        WebView videoWeb;
            public PostViewHolder(View itemView) {
                super(itemView);
                textViewPostContent = itemView.findViewById(R.id.text_post_content);
                imageView = itemView.findViewById(R.id.image_user_avatar);
                textViewUserName = itemView.findViewById(R.id.text_user_name);
                videoWeb = (WebView) itemView.findViewById(R.id.web_video);
                videoWeb.getSettings().setJavaScriptEnabled(true);
                videoWeb.setWebChromeClient(new WebChromeClient() {
                });
            }

        }
}