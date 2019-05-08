package com.kaoyan.kaoyandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.info.CommunityInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CommunityInfo> list;
    private boolean isMyCollect;
    private OnClickCollectListener onClickCollectListener;

    public CommunityAdapter(Context context, List<CommunityInfo> list, boolean isMyCollect) {
        this.context = context;
        this.list = list;
        this.isMyCollect = isMyCollect;
    }

    public void setOnClickCollectListener(OnClickCollectListener onClickCollectListener) {
        this.onClickCollectListener = onClickCollectListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_community, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CommunityInfo communityInfo = list.get(position);
        viewHolder.name.setText(communityInfo.getTitle());
        viewHolder.content.setText(communityInfo.getContent());
        if (isMyCollect) {
            viewHolder.collect.setVisibility(View.GONE);
        } else {
            viewHolder.collect.setVisibility(View.VISIBLE);
            viewHolder.collect.setChecked(communityInfo.isCheck());
        }
        viewHolder.collect.setOnClickListener(view ->
            onClickCollectListener.click(position, viewHolder.collect.isChecked()));
        viewHolder.comment.setOnClickListener(view ->
            onClickCollectListener.comment(position));
        viewHolder.itemView.setOnClickListener(view ->
            onClickCollectListener.onItemClick(position));
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
        @BindView(R.id.collect)
        CheckBox collect;
        @BindView(R.id.comment)
        ImageView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickCollectListener {
        void click(int position, boolean isAdd);

        void comment(int position);

        void onItemClick(int position);
    }
}
