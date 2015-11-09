package com.bitdubai.fermat_ccp_core.layer.desktop_module;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.middleware.wallet_contacts.WalletContactsPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.middleware.wallet_manager.WalletManagerPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopModuleLayer extends AbstractLayer {

    public DesktopModuleLayer() {
        super(Layers.DESKTOP_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new WalletContactsPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
