package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.search_view;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuPressEvent;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
public class SearchViewOnPressEvent implements OptionMenuPressEvent<SearchViewOnPressEvent> {

    /**
     * Title when search is focus
     */
    private boolean isToolbarTitleVisible;

    /**
     * Change the title when the search view is focus
     */
    private String titleWhenIsFocus;


    public SearchViewOnPressEvent() {
    }

    public boolean isToolbarTitleVisible() {
        return isToolbarTitleVisible;
    }

    public void setIsToolbarTitleVisible(boolean isToolbarTitleVisible) {
        this.isToolbarTitleVisible = isToolbarTitleVisible;
    }

    public String getTitleWhenIsFocus() {
        return titleWhenIsFocus;
    }

    public void setTitleWhenIsFocus(String titleWhenIsFocus) {
        this.titleWhenIsFocus = titleWhenIsFocus;
    }

    @Override
    public SearchViewOnPressEvent onPress() {
        return this;
    }
}