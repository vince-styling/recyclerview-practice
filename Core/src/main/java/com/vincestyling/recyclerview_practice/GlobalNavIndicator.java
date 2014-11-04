package com.vincestyling.recyclerview_practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GlobalNavIndicator extends View {

    public GlobalNavIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMenuDatas = new int[][] {
                {R.drawable.global_nav_bq_off, R.drawable.global_nav_bq_on},
                {R.drawable.global_nav_me_off, R.drawable.global_nav_me_on},
                {R.drawable.global_nav_ngc_off, R.drawable.global_nav_ngc_on},
                {R.drawable.global_nav_plaza_off, R.drawable.global_nav_plaza_on}
        };
    }

    private boolean mInitialized;

    // normally grid's cache bitmap.
    private Bitmap mItemsBitmap;

    private int mSelectedItemIndex, mTempSelectedItemIndex;

    private int mItemCount;
    private float mItemWidth, mItemHeight;
    private int mCentreDistance;
    private int[][] mMenuDatas;

    private void init() {
        final int count = mMenuDatas.length;
        if (count == 0) return;

        mInitialized = true;
        int width = getWidth();
        int height = getHeight();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.start_to_record_normal, opts);
        mCentreDistance = opts.outWidth;

        mItemCount = count;
        mItemWidth = (width - mCentreDistance) / mItemCount;
        mItemHeight = height;

        mItemsBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas tmpCanvas = new Canvas(mItemsBitmap);
        drawItems(tmpCanvas);
    }

    private void drawItems(Canvas canvas) {
        final int count = mMenuDatas.length;
        for (int index = 0; index < count; index++) {
            RectF itemRect = getItemRect(index);

            canvas.save(Canvas.CLIP_SAVE_FLAG);
            canvas.clipRect(itemRect);

            drawItem(canvas, itemRect, index, false);

            canvas.restore();
        }
    }

    private RectF getItemRect(int index) {
        RectF itemRect = new RectF();
        itemRect.left = (index % mItemCount) * mItemWidth;
        if (index >= mItemCount / 2) {
            itemRect.left += mCentreDistance;
        }
        itemRect.right = itemRect.left + mItemWidth;
        itemRect.top = mItemHeight * (index / mItemCount);
        itemRect.bottom = itemRect.top + mItemHeight;
        return itemRect;
    }

    private void drawItem(Canvas canvas, RectF itemRect, int index, boolean isOn) {
        int resId = isOn ? mMenuDatas[index][1] : mMenuDatas[index][0];
        Bitmap itemBitmap = BitmapFactory.decodeResource(getResources(), resId);
        getItemBounds(itemRect, itemBitmap);
        canvas.drawBitmap(itemBitmap, null, itemRect, null);
    }

    private void getItemBounds(RectF itemRect, Bitmap itemBitmap) {
        itemRect.left += (itemRect.width() - itemBitmap.getWidth()) / 2;
        itemRect.right = itemRect.left + itemBitmap.getWidth();
        itemRect.top += (itemRect.height() - itemBitmap.getHeight()) / 2;
        itemRect.bottom = itemRect.top + itemBitmap.getHeight();
    }

    private void highlightItem(Canvas canvas) {
        RectF itemRect = getItemRect(mTempSelectedItemIndex);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(itemRect);

        drawItem(canvas, itemRect, mTempSelectedItemIndex, true);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int index = 0; index < mItemCount; index++) {
                    RectF itemRect = getItemRect(index);
                    if (itemRect.contains((int) event.getX(), (int) event.getY())) {
                        if (index == mTempSelectedItemIndex) return false;
                        mTempSelectedItemIndex = index;
                        invalidate();
                        break;
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (getItemRect(mTempSelectedItemIndex).contains((int) event.getX(), (int) event.getY())) return true;
                mTempSelectedItemIndex = mSelectedItemIndex;
                invalidate();
                return false;
            case MotionEvent.ACTION_UP:
                if (mSelectedItemIndex != mTempSelectedItemIndex) {
                    mSelectedItemIndex = mTempSelectedItemIndex;
                    invalidate();
                }
                return false;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mInitialized) init();
        canvas.drawBitmap(mItemsBitmap, 0, 0, null);
        highlightItem(canvas);
    }
}
