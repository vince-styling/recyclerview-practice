package com.vincestyling.recyclerview_practice;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.vincestyling.recyclerview_practice.entity.*;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SparseArray<Data> viewDatas = new SparseArray<Data>(3);
    private VideoViewPool mViewPool;
    private List<Data> mItems;

    public SimpleAdapter(List<Data> items, VideoViewPool mViewPool) {
        this.mViewPool = mViewPool;
        mItems = items;

        new FavoriteGameTitleData(viewDatas);
        new MyGameTitleData(viewDatas);
        new WithoutGameData(viewDatas);
        new GameData(viewDatas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
        return viewDatas.get(viewType).onCreateViewHolder(container, mViewPool);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        mItems.get(position).onBindViewHolder(itemHolder);
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
