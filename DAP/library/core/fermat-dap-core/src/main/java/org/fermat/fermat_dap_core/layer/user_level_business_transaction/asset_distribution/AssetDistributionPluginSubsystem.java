package org.fermat.fermat_dap_core.layer.user_level_business_transaction.asset_distribution;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class AssetDistributionPluginSubsystem extends AbstractPluginSubsystem {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public AssetDistributionPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_DISTRIBUTION));
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartSubsystemException {

    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
