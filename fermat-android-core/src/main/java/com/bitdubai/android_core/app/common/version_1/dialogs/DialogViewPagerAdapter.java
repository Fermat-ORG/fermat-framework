package com.bitdubai.android_core.app.common.version_1.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.adapters.AbstractViewPagerAdapter;

import java.util.List;

/**
 * Created by mati on 2016.04.05..
 */
public class DialogViewPagerAdapter extends AbstractViewPagerAdapter {


    public DialogViewPagerAdapter(Context context, List lstItems) {
        super(context, lstItems);
    }

    @Override
    protected void bindHolder(View item_view, int position, Object item) {
        switch (position){
            case 0:
                item_view.setBackgroundColor(Color.CYAN);
                break;
            case 1:
                item_view.setBackgroundColor(Color.RED);
                break;
        }
    }


    @Override
    protected int getItemLayout(Object item, int position) {
        return R.layout.welcome_dialog_first;
    }
}
