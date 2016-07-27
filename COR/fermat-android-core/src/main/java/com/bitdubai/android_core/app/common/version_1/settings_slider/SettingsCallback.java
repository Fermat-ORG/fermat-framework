package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.view.View;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public interface SettingsCallback<I> {

    void onItemClickListener(View view, I item, int position, View... views);
}
