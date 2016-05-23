package com.bitdubai.fermat_android_api.engine;

import android.content.Context;

/**
 * Created by mati on 2015.11.23
 */
public interface ElementsWithAnimation{

    void startCollapseAnimation(int verticalOffset);

    void startExpandAnimation(int verticalOffSet);

    void startCollapseAnimation(Context context, int verticalOffset);

    void startExpandAnimation(Context context, int verticalOffSet);
}
