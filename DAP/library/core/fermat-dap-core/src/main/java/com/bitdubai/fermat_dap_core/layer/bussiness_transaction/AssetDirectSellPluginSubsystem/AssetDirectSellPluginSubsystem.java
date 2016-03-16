package com.bitdubai.fermat_dap_core.layer.bussiness_transaction.AssetDirectSellPluginSubsystem;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public class AssetDirectSellPluginSubsystem extends AbstractPluginSubsystem {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public AssetDirectSellPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_DIRECT_SELL));
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartSubsystemException {
        try {
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
