package com.bitdubai.fermat_tky_core.layer.sub_app_module;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_tky_core.layer.sub_app_module.artist.TokenlySubAppModuleArtistIdentityPluginSubsystem;
import com.bitdubai.fermat_tky_core.layer.sub_app_module.fan.TokenlySubAppModuleFanIdentityPluginSubsystem;


/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/18/16.
 */
public class SubAppModuleLayer extends AbstractLayer{

    public SubAppModuleLayer() {
        super(Layers.SUB_APP_MODULE);
    }

    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new TokenlySubAppModuleFanIdentityPluginSubsystem());
            registerPlugin(new TokenlySubAppModuleArtistIdentityPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
