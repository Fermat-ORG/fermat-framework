package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class OptionMenuItem extends MenuItem {

    /**
     * Android ShowAsActionClass
     */
    private int showAsAction = -1;
    /**
     * Group
     */
    private int groupId;
    /**
     * Order
     */
    private int order;
    /**
     * Class accepted for the framework
     * eg: SearchView.  See OptionMenuViewsAvailables class in Fermat-Api to know available codes
     */
    private int actionViewClass = -1;

    /**
     * OptionMenu press event launched when user click on the item
     */
    private OptionMenuPressEvent<?> optionMenuPressEvent;

    public OptionMenuItem(int id) {
        super(id);
    }

    public int getShowAsAction() {
        return showAsAction;
    }

    public void setShowAsAction(int showAsAction) {
        this.showAsAction = showAsAction;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getOrder() {
        return order;
    }

    public void setActionViewClass(int actionViewClass) {
        this.actionViewClass = actionViewClass;
    }

    public int getActionViewClass() {
        return actionViewClass;
    }

    public OptionMenuPressEvent<?> getOptionMenuPressEvent() {
        return optionMenuPressEvent;
    }

    public void setOptionMenuPressEvent(OptionMenuPressEvent<?> optionMenuPressEvent) {
        this.optionMenuPressEvent = optionMenuPressEvent;
    }
}
