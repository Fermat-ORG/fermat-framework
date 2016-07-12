package com.bitdubai.fermat_bch_core.layer.crypto_network.fermat_network;

import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Matias Furszyfer
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatNetworkPluginSubsystem extends AbstractPluginSubsystem {

    public FermatNetworkPluginSubsystem(FermatContext fermatContext) {
        super(new PluginReference(Plugins.FERMAT_NETWORK),fermatContext);
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
//            try {
//                registerDeveloperMati("com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.DeveloperBitDubai");
//            }catch (Exception e){
//                System.err.println("##############################################\n");
//                System.err.println("##############################################\n");
//                System.err.println("Fermat network not found");
//                System.err.println("Search type:"+AbstractPluginDeveloper.class.getName());
//                e.printStackTrace();
//                System.err.println("##############################################\n");
//                System.err.println("##############################################\n");
//            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }


}
