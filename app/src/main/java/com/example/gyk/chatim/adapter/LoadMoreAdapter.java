package com.example.gyk.chatim.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gyk.chatim.R;
import com.example.gyk.chatim.bean.MessBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Author : Gyk
 * CreateBy : 2019/02/25/16:04
 * PackageName : com.example.gyk.chatim.adapter
 * Describe : TODO
 **/
public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessBean.ResultBean> dataList;

    //普通布局
    private final int TYPE_ITEM = 1;
    //脚布局
    private final int TYPE_FOOTER = 2;
    //当前加载状态，默认为加载完成
    private int loadState = 2;
    //正在加载
    public final int LOADING = 1;
    //加载完成
    public final int LOADING_COMPLETE = 2;
    //加载到底
    public final int LOADING_END = 3;

    public LoadMoreAdapter(List<MessBean.ResultBean> mDataList) {
        this.dataList = mDataList;
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_infomation, viewGroup, false);
            return new RecyclerViewHolder(view);
        } else if (position == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loadmore_footview, viewGroup, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecyclerViewHolder) {
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            recyclerViewHolder.sdv_mg.setImageURI(dataList.get(position).getFileUrl());
            recyclerViewHolder.tv_head.setText(dataList.get(position).getTitle());
            recyclerViewHolder.time.setText(dataList.get(position).getPublicTime());
        } else if (viewHolder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (loadState) {
                case LOADING:
                    footViewHolder.loading_progress.setVisibility(View.VISIBLE);
                    footViewHolder.loading_text.setVisibility(View.VISIBLE);
                    footViewHolder.load_more_load_end_view.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    footViewHolder.loading_progress.setVisibility(View.INVISIBLE);
                    footViewHolder.loading_text.setVisibility(View.INVISIBLE);
                    footViewHolder.load_more_load_end_view.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    footViewHolder.loading_progress.setVisibility(View.GONE);
                    footViewHolder.loading_text.setVisibility(View.GONE);
                    footViewHolder.load_more_load_end_view.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdv_mg;
        TextView tv_head;
        TextView time;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            sdv_mg = itemView.findViewById(R.id.sdv_mg);
            tv_head = (TextView) itemView.findViewById(R.id.tv_head);
            time = itemView.findViewById(R.id.time);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar loading_progress;
        TextView loading_text;
        FrameLayout load_more_load_end_view;

        FootViewHolder(View itemView) {
            super(itemView);
            loading_progress = (ProgressBar) itemView.findViewById(R.id.loading_progress);
            loading_text = (TextView) itemView.findViewById(R.id.loading_text);
            load_more_load_end_view = (FrameLayout) itemView.findViewById(R.id.load_more_load_end_view);
        }
    }

    /**
     * 设置上拉状态
     *
     * @param loadState 1:正在加载 2:加载完成 3:加载到底
     **/
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
