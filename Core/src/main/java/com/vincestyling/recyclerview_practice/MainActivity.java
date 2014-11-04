package com.vincestyling.recyclerview_practice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.vincestyling.recyclerview_practice.entity.Data;

import java.util.List;

public class MainActivity extends Activity {
    public static int STAT_GAME_VIDEO_ITEM_COUNT;

    private RecyclerView mLsvMain;
    private TextView mTxvLoadingTip;
    private View mLotLoading, mPrbLoading;
    private RecyclerView.RecycledViewPool mViewPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLsvMain = (RecyclerView) findViewById(R.id.lsvMain);
        mLsvMain.setLayoutManager(new LinearLayoutManager(this));

        mViewPool = new RecyclerView.RecycledViewPool();
//        mLsvMain.setRecycledViewPool(mViewPool);

        mTxvLoadingTip = (TextView) findViewById(R.id.txvLoadingTip);
        mLotLoading = findViewById(R.id.lotLoading);
        mPrbLoading = findViewById(R.id.prbLoading);

        Netroid.getGameList(new Listener<List<Data>>() {
            @Override
            public void onSuccess(List<Data> games) {
                mLotLoading.setVisibility(View.GONE);
                mLsvMain.setVisibility(View.VISIBLE);
                mLsvMain.setAdapter(new SimpleAdapter(games, mViewPool));
            }

            @Override
            public void onError(NetroidError error) {
                mTxvLoadingTip.setText(R.string.loading_failed);
                mPrbLoading.setVisibility(View.GONE);
            }
        });
    }
}
