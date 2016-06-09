package com.bitdubai.sub_app.wallet_manager.commons;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.interface_objects.FermatInterfaceObject;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

/**
 * Created by mati on 2015.11.21..
 */
public class EmptyItem implements FermatInterfaceObject {

    int iconResource;
    int position;

    public EmptyItem(int iconResource, int position) {
        this.iconResource = iconResource;
        this.position = position;
    }

    @Override
    public InterfaceType getType() {
        return InterfaceType.EMPTY;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public void setIconResource(int iconRes) {
        this.iconResource = iconRes;
    }

    @Override
    public int getIconResource() {
        return iconResource;
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
