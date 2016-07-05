package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.tab_layout;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.drawables.Badge;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
public class TabBadgeView extends FermatView {

    private String title;
    private Badge badge;
    private float titleSize = 24;

    public TabBadgeView() {
        super(100, SourceLocation.FERMAT_FRAMEWORK);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public float getTitleSize() {
        return titleSize;
    }
}
