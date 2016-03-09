package com.bitdubai.android_core.app.common.version_1.recents;

import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat.R;
import com.wirelesspienetwork.overview.model.ViewHolder;

/**
 * Created by mati on 2016.03.03..
 */
public class RecentHolder extends ViewHolder<View,RecentApp>{

    private ViewGroup root;

    public RecentHolder(View view) {
        super(view);
        root = (ViewGroup) view.findViewById(R.id.app_container);
    }

    public ViewGroup getRoot() {
        return root;
    }
}
