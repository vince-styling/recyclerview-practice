package com.vincestyling.recyclerview_practice.entity;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.vincestyling.recyclerview_practice.R;
import com.vincestyling.recyclerview_practice.VideoViewPool;

public class FavoriteGameTitleData extends Data {
    private static final int TYPE = 2;

    public FavoriteGameTitleData() {}

    public FavoriteGameTitleData(SparseArray<Data> viewDatas) {
        super(viewDatas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, VideoViewPool mViewPool) {
        return new RecyclerView.ViewHolder(inflate(container, R.layout.favorite_game_title_banner)) {};
    }

    @Override
    public int getViewType() {
        return TYPE;
    }
}
