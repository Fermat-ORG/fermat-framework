package com.bitdubai.android_core.app.common.version_1.builders.option_menu;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuViewsAvailables;


/**
 * Created by Matias Furszyfer on 2016.06.09..
 * This class will be used to enable or disable views
 */
public class OptionMenuFrameworkHelper {

    public static View obtainFrameworkAvailableOptionMenuItems(Context context,int id){
        View view = null;
        switch (id){
            case OptionMenuViewsAvailables.SEARCH_VIEW:
                view = new SearchView(context);
                break;
        }
        return view;
    }


}
