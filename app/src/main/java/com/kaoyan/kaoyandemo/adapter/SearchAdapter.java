package com.kaoyan.kaoyandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.info.SearchInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SearchInfo> list;

    public SearchAdapter(Context context, List<SearchInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SearchInfo searchInfo = list.get(position);
        viewHolder.school.setText("学校名称：" + searchInfo.getSchoolName());
        viewHolder.department.setText("院系名称：" + searchInfo.getCalssName());
        viewHolder.major.setText("专业名称：" + searchInfo.getMajorName());
        viewHolder.major_des.setText("专业描述：" + searchInfo.getMajorDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.school)
        TextView school;
        @BindView(R.id.department)
        TextView department;
        @BindView(R.id.major)
        TextView major;
        @BindView(R.id.major_des)
        TextView major_des;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
