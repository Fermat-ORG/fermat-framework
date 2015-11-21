package com.bitdubai.sub_app.wallet_manager.structure;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.interface_objects.FermatInterfaceObject;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

/**
 * Created by Matas Furszyfer on 2015.11.01..
 */
public class Item<I extends FermatInterfaceObject> implements FermatInterfaceObject {

    I object;



    public Item(I object) {
        this.object = object;
    }

    @Override
    public InterfaceType getType() {
        return (object instanceof InstalledWallet) ? InterfaceType.WALLET : InterfaceType.SUB_APP;
    }

    @Override
    public String getName() {
        return object.getName();
    }

    @Override
    public String getIcon() {
        return object.getIcon();
    }

    @Override
    public void setIconResource(int bitcoin_wallet) {
        object.setIconResource(bitcoin_wallet);
    }

    @Override
    public int getIconResource() {
        return object.getIconResource();
    }

    public I getInterfaceObject() {
        return object;
    }
}
