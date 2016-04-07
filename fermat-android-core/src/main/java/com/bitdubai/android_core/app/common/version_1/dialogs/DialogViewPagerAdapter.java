package com.bitdubai.android_core.app.common.version_1.dialogs;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.adapters.AbstractViewPagerAdapter;

import java.util.List;

/**
 * Created by mati on 2016.04.05..
 */
public class DialogViewPagerAdapter extends AbstractViewPagerAdapter<WelcomeDialogItem,DialogViewPagerHolder> {


    public DialogViewPagerAdapter(Context context, List<WelcomeDialogItem> lstItems) {
        super(context, lstItems);
    }

    @Override
    protected int getItemType(WelcomeDialogItem item, int position) {
        return 0;
    }

    @Override
    protected DialogViewPagerHolder createHolder(View item_view, int position,int itemType) {
        return new DialogViewPagerHolder(item_view,itemType);
    }

    @Override
    protected void bindHolder(DialogViewPagerHolder fermatViewHolder, int position, WelcomeDialogItem item) {
        fermatViewHolder.txt_title.setText(item.getTitle());
        fermatViewHolder.first_paragraph.setText(item.getFirstParagraphs());
        fermatViewHolder.second_paragraph.setText(item.getSecondParagraphs());
        fermatViewHolder.third_paragraph.setText(item.getThirdParagraphs());

    }

    @Override
    protected int getItemLayout(WelcomeDialogItem item, int position, int itemType) {
        return R.layout.welcome_dialog_first;
    }


}
