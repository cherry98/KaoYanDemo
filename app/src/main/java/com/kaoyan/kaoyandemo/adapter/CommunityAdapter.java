package com.kaoyan.kaoyandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.R;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityAdapter extends RecyclerView.Adapter {

    private Context context;
    private List list;
    private boolean isMyCollect;

    public CommunityAdapter(Context context, List list, boolean isMyCollect) {
        this.context = context;
        this.list = list;
        this.isMyCollect = isMyCollect;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_community, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(((Map) list.get(position)).get("community_name") + "");
        viewHolder.content.setText(((Map) list.get(position)).get("community_content") + "");
        viewHolder.time.setText(((Map) list.get(position)).get("community_time") + "");
        if (isMyCollect) {
            viewHolder.collect.setVisibility(View.GONE);
        } else {
            viewHolder.collect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.collect)
        CheckBox collect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
