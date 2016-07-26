package com.bitdubai.android_core.app.common.version_1.util.res_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatTextViewRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.tab_layout.TabBadgeView;

import static com.bitdubai.fermat_android_api.core.FrameworkAvailableViews.TAB_WITH_ICON;

/**
 * Created by Matias Furszyfer on 2016.07.02..
 */
public class ResViewFrameworkHelper {

    public static View obtainView(Context context, FermatView fermatView) {
        View view = null;
        switch (fermatView.getId()) {
            case TAB_WITH_ICON:
                TabBadgeView tabBadgeView = (TabBadgeView) fermatView;
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.custom_tab, null);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_tab_title);
                FermatTextViewRuntime fermatTextViewRuntime = tabBadgeView.getFermatTextViewRuntime();
                fermatTextView.setText(fermatTextViewRuntime.getTitle());
                fermatTextView.setTextSize(fermatTextViewRuntime.getTitleSize());
                fermatTextView.setFont(fermatTextViewRuntime.getFontType());
                Drawable drawable = ResourceLocationSearcherHelper.obtainDrawable(context, tabBadgeView.getBadge());
                View badgeView = view.findViewById(R.id.badge);
                badgeView.setBackground(drawable);
                FrameLayout.LayoutParams lps = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lps.gravity = Gravity.RIGHT;
                badgeView.setLayoutParams(lps);
                break;
        }
        return view;
    }


}
