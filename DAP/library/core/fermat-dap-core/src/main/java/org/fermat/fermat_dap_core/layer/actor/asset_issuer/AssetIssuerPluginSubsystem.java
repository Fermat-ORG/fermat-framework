package org.fermat.fermat_dap_core.layer.actor.asset_issuer;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.DeveloperBitDubai;

/**
 * Created by PatricioGesualdi - (pmgesualdi@hotmail.com) on 10/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetIssuerPluginSubsystem extends AbstractPluginSubsystem {

    public AssetIssuerPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_ISSUER));
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
