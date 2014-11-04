package com.vincestyling.recyclerview_practice.entity;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.vincestyling.recyclerview_practice.R;

public class MyGameTitleData extends Data {
    private static final int TYPE = 1;

    public MyGameTitleData() {}

    public MyGameTitleData(SparseArray<Data> viewDatas) {
        super(viewDatas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, RecyclerView.RecycledViewPool mViewPool) {
        return new RecyclerView.ViewHolder(inflate(container, R.layout.my_game_title_banner)) {};
    }

    @Override
    public int getViewType() {
        return TYPE;
    }
}
