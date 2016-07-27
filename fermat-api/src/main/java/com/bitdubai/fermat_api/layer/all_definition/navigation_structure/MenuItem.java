package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatMenuItem;

import java.io.Serializable;


/**
 * Created by Matias Furszyfer on 2015.07.17..
 */
public class MenuItem implements FermatMenuItem, Serializable {

    /**
     * MenuItem class member variables
     */
    String label;

    String icon;

    Activities linkToActivity;
    private String appLinkPublicKey;

    boolean selected = false;
    private int notifications;
    private int id;

    //This could be an icon or whatever the develoer want
    private FermatDrawable fermatDrawable;
    // background color when is selected
    private String backgroundSelectedColor;
    //text color
    private String textColor;
    private String selectedTextColor;

    /**
     * Visibility
     */
    private boolean visibility = true;

    /**
     * SideMenu class constructors
     */
    public MenuItem(int id) {
        this.id = id;
    }

    public MenuItem() {
    }

    public MenuItem(String label, String icon, Activities linkToActivity, String appLinkPublicKey) {
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

    public int getId() {
        return id;
    }

    public void setFermatDrawable(FermatDrawable itemIcon) {
        this.fermatDrawable = itemIcon;
    }

    public FermatDrawable getFermatDrawable() {
        return fermatDrawable;
    }

    public void backgroundSelectedColor(String color) {
        this.backgroundSelectedColor = color;
    }

    public String getBackgroundSelectedColor() {
        return backgroundSelectedColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(String selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
