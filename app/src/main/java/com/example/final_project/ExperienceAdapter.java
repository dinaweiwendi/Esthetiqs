package com.example.final_project;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project.Model.Experience;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> implements Filterable {

    private Context ctx;
    private List<Experience> exp_list;

    public ExperienceAdapter(Context ctx, List<Experience> exp_list) {
        this.ctx = ctx;
        this.exp_list = exp_list;
    }

    @Override
    public ExperienceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.exp_card_layout, null);
        return new ExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExperienceViewHolder holder, int position) {
        Experience exp = exp_list.get(position);
        holder.textView.setText(exp.getexp());
        holder.delete.setTag(position);
    }

    @Override
    public int getItemCount() {
        return exp_list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView delete;

        public ExperienceViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.exp);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int)delete.getTag();
                    exp_list.remove(pos);
                    notifyItemRemoved(pos);
                    notifyDataSetChanged();
                    //TODO: update firebase
                }
            });
        }


    }

}


