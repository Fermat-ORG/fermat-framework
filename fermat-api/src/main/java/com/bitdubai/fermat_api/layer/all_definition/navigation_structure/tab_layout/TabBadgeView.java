package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.tab_layout;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
public class TabBadgeView extends FermatView {

    private String title;
    private int notificationNumbers;

    public TabBadgeView() {
    }

    public TabBadgeView(int id, SourceLocation sourceLocation) {
        super(id, null, sourceLocation);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNotificationNumbers() {
        return notificationNumbers;
    }

    public void setNotificationNumbers(int notificationNumbers) {
        this.notificationNumbers = notificationNumbers;
    }
}
