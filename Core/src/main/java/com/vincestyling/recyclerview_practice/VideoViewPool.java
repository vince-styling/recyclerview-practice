package com.vincestyling.recyclerview_practice;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.vincestyling.recyclerview_practice.entity.Video;

import java.util.LinkedList;

public class VideoViewPool {
    private LinkedList<View> mViewHeap = new LinkedList<View>();
    public int STAT_GAME_VIDEO_ITEM_COUNT;

    public synchronized View getView(GridLayout gridVideo) {
        View view = mViewHeap.poll();
        if (view == null) {
            view = LayoutInflater.from(gridVideo.getContext()).inflate(R.layout.game_video_item, gridVideo, false);
//            Log.e("", String.format("Video item createViewCount : %d", ++STAT_GAME_VIDEO_ITEM_COUNT));
            view.setTag(new VideoHolder(view));
        }
        return view;
    }

    public synchronized void recycleView(View view) {
        mViewHeap.add(view);
    }

    public int size() {
        return mViewHeap.size();
    }

    public class VideoHolder {
        TextView txvVideoTitle;
        NetworkImageView imvVideoCover;
        public VideoHolder(View itemView) {
            txvVideoTitle = (TextView) itemView.findViewById(R.id.txvVideoTitle);
            imvVideoCover = (NetworkImageView) itemView.findViewById(R.id.imvVideoCover);
        }

        public void bindView(Video video) {
            Netroid.displayImage(video.getScreenshot(), imvVideoCover);
            txvVideoTitle.setText(video.getTitle());
        }
    }
}