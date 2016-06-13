package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class OptionMenuItem extends MenuItem {

    private int showAsAction = -1;
    private int groupId;
    private int order;
    /**
     * Class accepted for the framework
     * eg: SearchView.  See OptionMenuViewsAvailables class in Android-Api to know codes
     */
    private int actionViewClass = -1;

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
}
