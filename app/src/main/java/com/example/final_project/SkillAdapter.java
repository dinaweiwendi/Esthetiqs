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

import com.example.final_project.Model.Skill;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> implements Filterable {

    private Context ctx;
    private List<Skill> skillList;

    public SkillAdapter(Context ctx, List<Skill> skillList) {
        this.ctx = ctx;
        this.skillList = skillList;
    }

    @Override
    public SkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.skill_card_layout, null);
        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SkillViewHolder holder, int position) {
        Skill skill = skillList.get(position);
        holder.textView.setText(skill.getskill());
        holder.delete.setTag(position);
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView delete;

        public SkillViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.skill);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) delete.getTag();
                    skillList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyDataSetChanged();

                }
            });


        }
    }
}

