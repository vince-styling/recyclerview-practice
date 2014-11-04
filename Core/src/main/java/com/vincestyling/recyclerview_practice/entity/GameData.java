package com.vincestyling.recyclerview_practice.entity;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.vincestyling.recyclerview_practice.MainActivity;
import com.vincestyling.recyclerview_practice.Netroid;
import com.vincestyling.recyclerview_practice.R;

import java.util.List;

public class GameData extends Data {
    private static final int TYPE = 3;

    private Game mGame;

    public GameData(SparseArray<Data> viewDatas) {
        super(viewDatas);
    }

    public GameData(Game game) {
        mGame = game;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, RecyclerView.RecycledViewPool mViewPool) {
        return new ViewHolder(inflate(container, R.layout.game_item), mViewPool);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView txvGameName, txvReadCount, txvVideoCount;
        private NetworkImageView imvLogo;
        private RecyclerView gridVideo;

        public ViewHolder(View itemView, RecyclerView.RecycledViewPool mViewPool) {
            super(itemView);

            this.itemView = itemView;
            imvLogo = (NetworkImageView) itemView.findViewById(R.id.imvLogo);
            txvGameName = (TextView) itemView.findViewById(R.id.txvGameName);
            txvReadCount = (TextView) itemView.findViewById(R.id.txvReadCount);
            txvVideoCount = (TextView) itemView.findViewById(R.id.txvVideoCount);

            gridVideo = (RecyclerView) itemView.findViewById(R.id.gridVideo);
            StaggeredGridLayoutManager gridLayout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            gridVideo.setLayoutManager(gridLayout);
            gridVideo.setRecycledViewPool(mViewPool);
            gridVideo.addItemDecoration(new RecyclerView.ItemDecoration() {
                // this value should be retrieving from res/values.
                private int mInsets = 10;

                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(mInsets, mInsets, mInsets, mInsets);
                }
            });
        }

        public void bindView(Game game) {
            itemView.setBackgroundColor(game.isFavorite() ? 0xfff8f8f8 : Color.TRANSPARENT);

            txvGameName.setText(game.getName());
            Netroid.displayImage(game.getLogoUrl(), imvLogo);
            txvReadCount.setText(String.valueOf(game.getReadCount()));
            txvVideoCount.setText(String.format("%d +%d", game.getVideoCount(), game.getRecentAddedCount()));

            VideoAdapter adapter = (VideoAdapter) gridVideo.getAdapter();
            if (adapter == null) {
                adapter = new VideoAdapter(game.getVideoList());
                gridVideo.setAdapter(adapter);
            } else {
                adapter.setVideoList(game.getVideoList());
            }
            int rowCount = (adapter.getItemCount() - 1) / 2 + 1;
            gridVideo.getLayoutParams().height = rowCount * 242;
        }
    }

    private static class GridViewHolder extends RecyclerView.ViewHolder {
        TextView txvVideoTitle;
        NetworkImageView imvVideoCover;
        public GridViewHolder(View itemView) {
            super(itemView);
            txvVideoTitle = (TextView) itemView.findViewById(R.id.txvVideoTitle);
            imvVideoCover = (NetworkImageView) itemView.findViewById(R.id.imvVideoCover);
        }

        public void bindView(Video video) {
            Netroid.displayImage(video.getScreenshot(), imvVideoCover);
            txvVideoTitle.setText(video.getTitle());
        }
    }

    private static class VideoAdapter extends RecyclerView.Adapter<GridViewHolder> {
        private static final int TYPE = 4;
        private List<Video> mVideoList;

        private VideoAdapter(List<Video> videoList) {
            mVideoList = videoList;
        }

        public void setVideoList(List<Video> mVideoList) {
            this.mVideoList = mVideoList;
            notifyDataSetChanged();
        }

        @Override
        public GridViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
            Log.e("", String.format("createViewCount : %d", ++MainActivity.STAT_GAME_VIDEO_ITEM_COUNT));
            return new GridViewHolder(inflate(container, R.layout.game_video_item));
        }

        @Override
        public void onBindViewHolder(GridViewHolder holder, int position) {
            Video video = mVideoList.get(position);
            holder.bindView(video);
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE;
        }

        @Override
        public int getItemCount() {
            return mVideoList.size();
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
