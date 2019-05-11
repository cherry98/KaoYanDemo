package com.kaoyan.kaoyandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.info.CommentInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 评论页面适配器
 */
public class CommentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CommentInfo> list;

    public CommentAdapter(Context context, List<CommentInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        CommentInfo commentInfo = list.get(position);
        viewHolder.content.setText("内容：" + commentInfo.getContent());
        viewHolder.time.setText("时间：" + commentInfo.getCreate_time());
        viewHolder.name.setText("名字：" + commentInfo.getRealName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.name)
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
