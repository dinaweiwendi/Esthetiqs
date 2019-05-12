package com.example.final_project;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project.Model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> implements Filterable {

    private Context ctx;
    private List<Video> videoList;

    public VideoAdapter(Context ctx, List<Video> videoList) {
        this.ctx = ctx;
        this.videoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.video_card_layout, null);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.title.setText(video.getTitle());
        holder.webview.loadData( videoList.get(position).getVideoUrl(), "text/html" , "utf-8" );
        holder.delete.setTag(position);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        WebView webview;
        TextView title;
        ImageView delete;


        public VideoViewHolder(View itemView) {
            super(itemView);
            webview = itemView.findViewById(R.id.videoWebView);
            title = itemView.findViewById(R.id.title);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int)delete.getTag();
                    videoList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyDataSetChanged();
                    //TODO: update firebase
                }
            });
        }


    }

}

