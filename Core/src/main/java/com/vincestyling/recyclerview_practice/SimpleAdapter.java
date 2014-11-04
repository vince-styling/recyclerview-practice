package com.vincestyling.recyclerview_practice;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.vincestyling.recyclerview_practice.entity.Game;

import java.util.List;

public class SimpleAdapter extends BaseAdapter {
    private VideoViewPool mViewPool;
    private List<Game> mItems;

    public SimpleAdapter(List<Game> items, VideoViewPool mViewPool) {
        this.mViewPool = mViewPool;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Game getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bindView(getItem(position));
        return convertView;
    }

    private class ViewHolder {
//        private View itemView;
        private TextView txvGameName, txvReadCount, txvVideoCount;
        private NetworkImageView imvLogo;
        private GridLayout gridVideo;

        public ViewHolder(View itemView) {
//            this.itemView = itemView;

            gridVideo = (GridLayout) itemView.findViewById(R.id.gridVideo);

            imvLogo = (NetworkImageView) itemView.findViewById(R.id.imvLogo);
            txvGameName = (TextView) itemView.findViewById(R.id.txvGameName);
            txvReadCount = (TextView) itemView.findViewById(R.id.txvReadCount);
            txvVideoCount = (TextView) itemView.findViewById(R.id.txvVideoCount);
        }

        public void bindView(Game game) {
//            itemView.setBackgroundColor(game.isFavorite() ? 0xfff8f8f8 : Color.TRANSPARENT);

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

            Log.e("", String.format("Name %s, ViewPool size : %d",
                    game.getName(), mViewPool.size()));
            for (int i = 0; i < gridVideo.getChildCount(); i++) {
                VideoViewPool.VideoHolder holder =
                        (VideoViewPool.VideoHolder) gridVideo.getChildAt(i).getTag();
                holder.bindView(game.getVideoList().get(i));
            }
        }
    }
}
