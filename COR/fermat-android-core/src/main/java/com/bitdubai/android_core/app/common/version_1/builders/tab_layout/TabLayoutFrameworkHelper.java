package com.bitdubai.android_core.app.common.version_1.builders.tab_layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.Views.BadgeDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.tab_layout.TabsViewsAvailables;


/**
 * Created by Matias Furszyfer on 2016.06.09..
 * This class will be used to enable or disable views
 */
public class TabLayoutFrameworkHelper {

    public static View obtainFrameworkAvailableView(Context context,int id){
        return obtainFrameworkAvailableView(context, id, null);
    }


    public static View obtainFrameworkAvailableView(Context context, int id, Object[] listeners) {
        View view = null;
        switch (id){
            case TabsViewsAvailables.TAB_NOTIFICATION_VIEW:
                View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
                View badge = v.findViewById(R.id.badge);
                badge.setBackground(new BadgeDrawable.BadgeDrawableBuilder(context).setTextSize(32).setPosition(BadgeDrawable.Position.TOP_RIGHT).build());
//                TextView tv = (TextView) v.findViewById(R.id.textView);
//                tv.setText(tabTitles[position]);
//                ImageView img = (ImageView) v.findViewById(R.id.imgView);
//                img.setImageResource(imageResId[position]);
//                return v;
                break;
        }
        return view;
    }
}
