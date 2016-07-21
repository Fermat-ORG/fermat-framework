package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.tab_layout;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatTextViewRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.drawables.Badge;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
public class TabBadgeView extends FermatView implements Serializable {

    private FermatTextViewRuntime fermatTextViewRuntime;
    private Badge badge;

    public TabBadgeView() {
        super(100, SourceLocation.FERMAT_FRAMEWORK);
    }


    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public FermatTextViewRuntime getFermatTextViewRuntime() {
        return fermatTextViewRuntime;
    }

    public void setFermatTextViewRuntime(FermatTextViewRuntime fermatTextViewRuntime) {
        this.fermatTextViewRuntime = fermatTextViewRuntime;
    }
}
