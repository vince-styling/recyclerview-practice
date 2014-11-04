//package com.vincestyling.recyclerview_practice.entity;
//
//import android.support.v7.widget.RecyclerView;
//import android.util.SparseArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import com.vincestyling.recyclerview_practice.VideoViewPool;
//
//public abstract class Data {
//    Data() {}
//
//    public Data(SparseArray<Data> viewDatas) {
//        viewDatas.put(getViewType(), this);
//    }
//
//    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, VideoViewPool mViewPool);
//    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder) {}
//    public abstract int getViewType();
//
//    protected static View inflate(ViewGroup container, int layoutId) {
//        return LayoutInflater.from(container.getContext()).inflate(layoutId, container, false);
//    }
//}
