package com.bitdubai.fermat_android_api.ui.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class FermatDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    /**
     * The orientation of the items in the RecyclerView is horizontal, so the divider is going to be drawn vertically
     */
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    /**
     * The orientation of the items in the RecyclerView is vertical, so the divider is going to be drawn horizontally
     */
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;
    private int leftMargin;


    /**
     * Create a item decorator that act has divider for a recyclerView with vertical orientation.
     * the orientation can be changed with {@link FermatDividerItemDecoration#setOrientation(int)}
     *
     * @param context the context activity where is the reciclerView
     */
    public FermatDividerItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(VERTICAL_LIST);
    }

    /**
     * Call {@link FermatDividerItemDecoration#FermatDividerItemDecoration(Context, int, int)} with orientation as {@link FermatDividerItemDecoration#VERTICAL_LIST}
     *
     * @param context the context activity where is the reciclerView
     * @param resId   the resource id use a drawable xml, for example a shape
     */
    public FermatDividerItemDecoration(Context context, int resId) {
        this(context, resId, VERTICAL_LIST);
    }

    /**
     * Create a {@link RecyclerView.ItemDecoration} that act has divider for a RecyclerView with horizontal or vertical orientation
     * using a resource (for example a shape drawable) to draw the divider
     *
     * @param context     the context activity where is the reciclerView
     * @param resId       the resource id use a drawable xml, for example a shape
     * @param orientation one of this: {@link FermatDividerItemDecoration#VERTICAL_LIST} or {@link FermatDividerItemDecoration#HORIZONTAL_LIST}
     */
    public FermatDividerItemDecoration(Context context, int resId, int orientation) {
        mDivider = ContextCompat.getDrawable(context, resId);
        setOrientation(orientation);
    }

    /**
     * Set the divider orientation
     *
     * @param orientation one of this: {@link FermatDividerItemDecoration#VERTICAL_LIST} or {@link FermatDividerItemDecoration#HORIZONTAL_LIST}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(canvas, parent);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    public void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    public void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left + leftMargin, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    public void setMargin(int left, int top, int rigth, int bottom) {
        this.leftMargin = left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
