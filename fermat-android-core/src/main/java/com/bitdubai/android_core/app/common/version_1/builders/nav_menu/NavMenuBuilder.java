package com.bitdubai.android_core.app.common.version_1.builders.nav_menu;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class NavMenuBuilder<S extends SideMenu> {

    private S sideMenu;

    public NavMenuBuilder(S sideMenu) {
        this.sideMenu = sideMenu;
    }

    /**
     * Set navigation header
     */
    public static FrameLayout buildHeader(Activity activity,NavigationViewPainter viewPainter) {
        if (viewPainter != null) {
            final View view = viewPainter.addNavigationViewHeader();
            FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.navigation_view_header);
            frameLayout.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            view.setLayoutParams(layoutParams);
            frameLayout.addView(view);
            return frameLayout;
        }
        return null;
    }

}
