package com.mati.fermat_navigator.drawer.interfaces;



import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatMenuItem;

/**
 * Created by mati on 2016.02.08..
 */
public class NavigatorMenuItem implements FermatMenuItem {


    /**
     * MenuItem class member variables
     */
    String label;

    String icon;

    Activities linkToActivity;
    private String appLinkPublicKey;

    boolean selected=false;
    private int notifications;

    /**
     * SideMenu class constructors
     */
    public NavigatorMenuItem() {
    }

    public NavigatorMenuItem(String label, String icon, Activities linkToActivity,String appLinkPublicKey) {
        this.label = label;
        this.icon = icon;
        this.linkToActivity = linkToActivity;
        this.appLinkPublicKey = appLinkPublicKey;
    }

    /**
     * SideMenu class setters
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLinkToActivity(Activities linkToActivity) {
        this.linkToActivity = linkToActivity;
    }

    /**
     * SideMenu class getters
     */
    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public int getDrawableSelected() {
        return 0;
    }

    public int getDrawableNormal() {
        return 0;
    }


    @Override
    public Activities getLinkToActivity() {
        return linkToActivity;
    }

    @Override
    public String getAppLinkPublicKey() {
        return appLinkPublicKey;
    }

    public void setAppLinkPublicKey(String appLinkPublicKey) {
        this.appLinkPublicKey = appLinkPublicKey;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }
}



