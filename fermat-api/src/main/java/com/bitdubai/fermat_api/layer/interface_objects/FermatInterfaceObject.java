package com.bitdubai.fermat_api.layer.interface_objects;

/**
 * Created by mati on 2015.11.01..
 */
public interface FermatInterfaceObject{

    InterfaceType getType();

    String getName();

    String getIcon();

    void setIconResource(int bitcoin_wallet);

    int getIconResource();

}
