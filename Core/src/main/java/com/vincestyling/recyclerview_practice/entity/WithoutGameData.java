package com.vincestyling.recyclerview_practice.entity;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.vincestyling.recyclerview_practice.R;

public class WithoutGameData extends Data {
    private static final int TYPE = 4;

    public WithoutGameData() {}

    public WithoutGameData(SparseArray<Data> viewDatas) {
        super(viewDatas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, RecyclerView.RecycledViewPool mViewPool) {
        return new RecyclerView.ViewHolder(inflate(container, R.layout.without_game_banner)) {};
    }

    @Override
    public int getViewType() {
        return TYPE;
    }
}
