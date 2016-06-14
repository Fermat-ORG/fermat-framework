package org.fermat.fermat_dap_core.layer.statistic_collector.asset_issuing;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class AssetIssuingPluginSubsystem extends AbstractPluginSubsystem {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public AssetIssuingPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_ISSUING));
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartSubsystemException {

    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
