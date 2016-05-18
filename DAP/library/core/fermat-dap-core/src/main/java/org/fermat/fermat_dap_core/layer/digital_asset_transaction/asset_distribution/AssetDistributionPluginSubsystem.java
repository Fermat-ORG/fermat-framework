package org.fermat.fermat_dap_core.layer.digital_asset_transaction.asset_distribution;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.DeveloperBitDubai;

/**
 * Created by lnacosta - (laion.cj91@gmail.com) on 11/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetDistributionPluginSubsystem extends AbstractPluginSubsystem {

    public AssetDistributionPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_DISTRIBUTION));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
