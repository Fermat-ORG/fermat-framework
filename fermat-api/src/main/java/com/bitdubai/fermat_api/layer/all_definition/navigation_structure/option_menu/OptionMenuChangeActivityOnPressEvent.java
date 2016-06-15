package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu;

/**
 * Created by Matias Furszyfer on 2016.06.15..
 */
public class OptionMenuChangeActivityOnPressEvent implements OptionMenuPressEvent<OptionMenuChangeActivityOnPressEvent>{

    /**
     * Activity code to open
     */
    private String activityCode;

    public OptionMenuChangeActivityOnPressEvent(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

    @Override
    public OptionMenuChangeActivityOnPressEvent onPress() {
        return this;
    }
}
