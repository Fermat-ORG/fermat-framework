package com.bitdubai.android_core.app.common.version_1.builders.option_menu;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuViewsAvailables;


/**
 * Created by Matias Furszyfer on 2016.06.09..
 * This class will be used to enable or disable views
 */
public class OptionMenuFrameworkHelper {

    public static View obtainFrameworkAvailableOptionMenuItems(Context context, int id) {
        return obtainFrameworkAvailableOptionMenuItems(context, id, null);
    }


    public static View obtainFrameworkAvailableOptionMenuItems(Context context, int id, Object[] listeners) {
        View view = null;
        switch (id) {
            case OptionMenuViewsAvailables.SEARCH_VIEW:
                android.widget.SearchView searchView = new android.widget.SearchView(context);
                if (listeners != null) {
                    for (Object listener : listeners) {
                        if (listener instanceof android.widget.SearchView.OnQueryTextListener) {
                            searchView.setOnQueryTextListener((android.widget.SearchView.OnQueryTextListener) listener);
                        } else if (listener instanceof android.widget.SearchView.OnCloseListener) {
                            searchView.setOnCloseListener((android.widget.SearchView.OnCloseListener) listener);
                        } else if (listener instanceof SearchView.OnSuggestionListener) {
                            searchView.setOnSuggestionListener((SearchView.OnSuggestionListener) listener);
                        } else if (listener instanceof View.OnClickListener) {
                            searchView.setOnClickListener((View.OnClickListener) listener);
                        }
                    }
                }
                view = searchView;
                break;
        }
        return view;
    }
}
