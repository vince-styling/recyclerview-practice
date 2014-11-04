package com.vincestyling.recyclerview_practice.entity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.vincestyling.recyclerview_practice.Netroid;
import com.vincestyling.recyclerview_practice.R;
import com.vincestyling.recyclerview_practice.VideoViewPool;

public class GameData extends Data {
    private static final int TYPE = 3;
    public int STAT_GAME_ITEM_COUNT;

    private Game mGame;

    public GameData(SparseArray<Data> viewDatas) {
        super(viewDatas);
    }

    public GameData(Game game) {
        mGame = game;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, VideoViewPool mViewPool) {
        Log.e("", String.format("Game item createViewCount : %d", ++STAT_GAME_ITEM_COUNT));
        return new ViewHolder(inflate(container, R.layout.game_item), mViewPool);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView txvGameName, txvReadCount, txvVideoCount;
        private NetworkImageView imvLogo;
        private GridLayout gridVideo;
        private VideoViewPool mViewPool;

        public ViewHolder(View itemView, VideoViewPool mViewPool) {
            super(itemView);
            this.itemView = itemView;
            this.mViewPool = mViewPool;

            gridVideo = (GridLayout) itemView.findViewById(R.id.gridVideo);

            imvLogo = (NetworkImageView) itemView.findViewById(R.id.imvLogo);
            txvGameName = (TextView) itemView.findViewById(R.id.txvGameName);
            txvReadCount = (TextView) itemView.findViewById(R.id.txvReadCount);
            txvVideoCount = (TextView) itemView.findViewById(R.id.txvVideoCount);
        }

        public void bindView(Game game) {
            itemView.setBackgroundColor(game.isFavorite() ? 0xfff8f8f8 : Color.TRANSPARENT);

            txvGameName.setText(game.getName());
            Netroid.displayImage(game.getLogoUrl(), imvLogo);
            txvReadCount.setText(String.valueOf(game.getReadCount()));
            txvVideoCount.setText(String.format("%d +%d", game.getVideoCount(), game.getRecentAddedCount()));

            int childCount = gridVideo.getChildCount();
            final int diffCount = childCount - game.getVideoList().size();
            for (int i = 0; i < diffCount; i++) {
                View view = gridVideo.getChildAt(--childCount);
                gridVideo.removeViewAt(childCount);
                mViewPool.recycleView(view);
            }

            for (int i = 0; i > diffCount; i--) {
                View view = mViewPool.getView(gridVideo);
                gridVideo.addView(view);
            }

            Log.e("", String.format("Name %s, Video Count : %d GridVideo ChildCount : %d, ViewPool size : %d",
                    game.getName(), game.getVideoList().size(), gridVideo.getChildCount(), mViewPool.size()));
            for (int i = 0; i < gridVideo.getChildCount(); i++) {
                VideoViewPool.VideoHolder holder =
                        (VideoViewPool.VideoHolder) gridVideo.getChildAt(i).getTag();
                holder.bindView(game.getVideoList().get(i));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder) {
        ((ViewHolder) itemHolder).bindView(mGame);
    }

    @Override
    public int getViewType() {
        return TYPE;
    }
}
