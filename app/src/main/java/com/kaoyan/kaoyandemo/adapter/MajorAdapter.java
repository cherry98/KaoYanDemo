package com.kaoyan.kaoyandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaoyan.kaoyandemo.R;
import com.kaoyan.kaoyandemo.info.SchoolInfo;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 专业列表适配器
 */
public class MajorAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SchoolInfo> list;
    private OnItemClickListener onItemClickListener;

    public MajorAdapter(Context context, List<SchoolInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_major, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SchoolInfo schoolInfo = list.get(position);
        viewHolder.majorid.setText(schoolInfo.getSchoolId());
        viewHolder.majorname.setText(schoolInfo.getSchoolName());
        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(schoolInfo.getSchoolId(),
            schoolInfo.getSchoolName()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.majorid)
        TextView majorid;
        @BindView(R.id.majorname)
        TextView majorname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String majorid, String majorname);
    }
}
