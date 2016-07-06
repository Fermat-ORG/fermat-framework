package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matias Furszyfer on 2016.06.07.
 * Edited by Nelson Ramirez (nelsoalfo@gmail.com) on 2016.06.18.
 */
public class OptionMenuItem extends MenuItem implements Serializable {
    /*
     * These should be kept in sync with MenuItem android class constants for showAsAction
     */

    /**
     * Never show this item as a button in an Action Bar.
     */
    public static final int SHOW_AS_ACTION_NEVER = 0;
    /**
     * Show this item as a button in an Action Bar if the system decides there is room for it.
     */
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
    /**
     * Visibility
     */
    private boolean visibility = true;

    /**
     * Set the ID og this {@link OptionMenuItem}. This is the ID your going to search in the method
     * <code>onOptionsItemSelected</code> of a fragment
     *
     * @param id the ID
     */
    public OptionMenuItem(int id) {
        super(id);
    }

    /**
     * @return the assigned ShowAsAction ID
     */
    public int getShowAsAction() {
        return showAsAction;
    }

    /**
     * assign ShowAsAction ID as in the android API: {@link OptionMenuItem#SHOW_AS_ACTION_NEVER},
     * {@link OptionMenuItem#SHOW_AS_ACTION_IF_ROOM}, {@link OptionMenuItem#SHOW_AS_ACTION_ALWAYS},
     * {@link OptionMenuItem#SHOW_AS_ACTION_WITH_TEXT} and {@link OptionMenuItem#SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW}
     *
     * @param showAsAction the ShowAsAction ID
     */
    public void setShowAsAction(int showAsAction) {
        this.showAsAction = showAsAction;
    }

    /**
     * Set the group ID
     *
     * @param groupId the group ID
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Set the position where is going to be showed this {@link OptionMenuItem}
     *
     * @param order the order or position
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the group ID
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @return the order od this option menu in the {@link OptionsMenu} or in a sub menu
     */
    public int getOrder() {
        return order;
    }

    public void setActionViewClass(int actionViewClass) {
        this.actionViewClass = actionViewClass;
    }

    public int getActionViewClass() {
        return actionViewClass;
    }

    /**
     * @return the {@link OptionMenuPressEvent} associated with this option menu
     */
    public OptionMenuPressEvent<?> getOptionMenuPressEvent() {
        return optionMenuPressEvent;
    }

    /**
     * Add a {@link OptionMenuPressEvent} to this {@link OptionMenuItem} to be fire when the option menu is pressed.
     * A example of a {@link OptionMenuPressEvent} is {@link OptionMenuChangeActivityOnPressEvent} the change the activiy
     * when the option menu is pressed
     *
     * @param optionMenuPressEvent the {@link OptionMenuPressEvent}
     */
    public void setOptionMenuPressEvent(OptionMenuPressEvent<?> optionMenuPressEvent) {
        this.optionMenuPressEvent = optionMenuPressEvent;
    }

    /**
     * @return <code>true</code> if this {@link OptionMenuItem} as a sub menu, <code>false</code> otherwise
     */
    public boolean hasSubMenu() {
        return subMenuOptionList != null;
    }

    /**
     * Add a {@link OptionMenuItem} to the sub menu of this {@link OptionMenuItem}
     *
     * @param optionMenuItem the option menu item to be added as sub menu
     */
    public void addSubMenuItem(OptionMenuItem optionMenuItem) {
        if (subMenuOptionList == null) subMenuOptionList = new ArrayList<>();
        subMenuOptionList.add(optionMenuItem);
    }

    /**
     * @return the list of {@link OptionMenuItem} in the sub menu
     */
    public List<OptionMenuItem> getSubMenuOptionList() {
        return subMenuOptionList;
    }

    /**
     * @return <code>true</code> if this {@link OptionMenuItem} is visible, <code>false</code> otherwise
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Set the visibility of this {@link OptionMenuItem}
     *
     * @param visibility <code>true</code> if is going to be visible, <code>false</code> otherwise
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
