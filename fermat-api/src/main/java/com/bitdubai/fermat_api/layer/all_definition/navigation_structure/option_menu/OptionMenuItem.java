package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class OptionMenuItem extends MenuItem {

    /*
     * These should be kept in sync with MenuItem android class constants for showAsAction
     */
    /** Never show this item as a button in an Action Bar. */
    public static final int SHOW_AS_ACTION_NEVER = 0;
    /** Show this item as a button in an Action Bar if the system decides there is room for it. */
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    /**
     * Always show this item as a button in an Action Bar.
     * Use sparingly! If too many items are set to always show in the Action Bar it can
     * crowd the Action Bar and degrade the user experience on devices with smaller screens.
     * A good rule of thumb is to have no more than 2 items set to always show at a time.
     */
    public static final int SHOW_AS_ACTION_ALWAYS = 2;

    /**
     * When this item is in the action bar, always show it with a text label even if
     * it also has an icon specified.
     */
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    /**
     * This item's action view collapses to a normal menu item.
     * When expanded, the action view temporarily takes over
     * a larger segment of its container.
     */
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;

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
     * subMenu
     */
    private List<OptionMenuItem> subMenuOptionList;

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

    public boolean hasSubMenu(){
        return subMenuOptionList!=null;
    }

    public void addSubMenuItem(OptionMenuItem optionMenuItem){
        if(subMenuOptionList==null) subMenuOptionList = new ArrayList<>();
        subMenuOptionList.add(optionMenuItem);
    }

    public List<OptionMenuItem> getSubMenuOptionList() {
        return subMenuOptionList;
    }


}
