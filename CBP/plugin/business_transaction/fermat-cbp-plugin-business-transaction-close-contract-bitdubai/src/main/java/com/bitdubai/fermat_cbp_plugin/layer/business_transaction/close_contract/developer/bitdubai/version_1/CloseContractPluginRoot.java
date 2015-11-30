package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * Created by Manuel Perez on 29/11/2015.
 */

public class CloseContractPluginRoot extends AbstractPlugin{

    public CloseContractPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start(){
        System.out.println("Close contract starting");
    }

}