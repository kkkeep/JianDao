package com.mr.k.libmvp.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerAdapter<D> extends RecyclerView.Adapter<BaseAdapterHolder<D>> {


    private OnItemClickListener<D> itemClickListener;

    private List<D> mDataList;

    public OnItemClickListener<D> getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener<D> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // 数据第一次加载回来调用该方法对adapter 设置数据
    public void setData(List<D> videoList){
        mDataList = videoList;
        notifyDataSetChanged();
    }


    // 对 recycler view 刷新时调用该方法
    public void refresh(List<D> videoList){
        setData(videoList);
    }


    // 加载更多的时候调用
    public void loadMore(List<D> videoList){
        int start = mDataList.size();
        mDataList.addAll(videoList);

        notifyItemRangeChanged(start,videoList.size());

    }

    public boolean isAdVideo(int position){
        return false;
    }


    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }



    public List<D> getDataList(){
        return  mDataList;
    }


    public D getDataByPosition(int position){

        return mDataList.get(position);
    }


    public abstract int getItemLayoutId(int viewType);


    public abstract BaseAdapterHolder<D> createHolder(View itemView,int viewType);

    @NonNull
    @Override
    public BaseAdapterHolder<D> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(viewType),parent,false);
        return createHolder(itemView,viewType);

    }


    @Override
    public void onBindViewHolder(@NonNull BaseAdapterHolder<D> holder, int position) {
        holder.bindData(mDataList.get(position));
    }
}
