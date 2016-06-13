package org.fermat.fermat_dap_core.layer.business_transaction.asset_unhold;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class AssetUnholdPluginSubsystem extends AbstractPluginSubsystem {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public AssetUnholdPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_UNHOLD));
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartSubsystemException {

    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
