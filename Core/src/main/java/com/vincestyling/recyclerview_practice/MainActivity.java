package com.vincestyling.recyclerview_practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.vincestyling.recyclerview_practice.entity.Game;

import java.util.List;

public class MainActivity extends Activity {
    private ListView mLsvMain;
    private TextView mTxvLoadingTip;
    private View mLotLoading, mPrbLoading;
    private VideoViewPool mViewPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLsvMain = (ListView) findViewById(R.id.lsvMain);

        mViewPool = new VideoViewPool();

        mTxvLoadingTip = (TextView) findViewById(R.id.txvLoadingTip);
        mLotLoading = findViewById(R.id.lotLoading);
        mPrbLoading = findViewById(R.id.prbLoading);

        Netroid.getGameList(new Listener<List<Game>>() {
            @Override
            public void onSuccess(List<Game> games) {
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
