package com.bitdubai.fermat_api.layer.interface_objects;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.desktop.Item;

import java.util.List;

/**
 * Created by mati on 2015.11.27..
 */
public class FermatFolder implements FermatInterfaceObject {


    private String name;
    private List<Item> lstFolderItems;
    private int iconRes;
    private int position;

    public FermatFolder(String name, List<Item> lstFolderItems, int position) {
        this.name = name;
        this.lstFolderItems = lstFolderItems;
        this.position = position;
    }

    public List<Item> getLstFolderItems() {
        return lstFolderItems;
    }

    @Override
    public InterfaceType getType() {
        return InterfaceType.FOLDER;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public void setIconResource(int iconRes) {
        this.iconRes = iconRes;
    }

    @Override
    public int getIconResource() {
        return iconRes;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public AppsStatus getAppStatus() {
        return null;
    }

    @Override
    public int getNotifications() {
        return 0;
    }
}
