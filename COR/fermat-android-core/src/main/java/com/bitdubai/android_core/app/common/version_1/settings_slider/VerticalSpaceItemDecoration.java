package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;
    private final int sidesSpace;

    public VerticalSpaceItemDecoration(int mVerticalSpaceHeight,int sidesSpace) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        this.sidesSpace = sidesSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
        outRect.right = sidesSpace;
        outRect.left = sidesSpace;
    }
}