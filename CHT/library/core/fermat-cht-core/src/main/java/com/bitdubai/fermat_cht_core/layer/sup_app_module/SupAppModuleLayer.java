package com.bitdubai.fermat_cht_core.layer.sup_app_module;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cht_core.layer.sup_app_module.actor_community.ChatActorCommunityModulePluginSubsystem;
import com.bitdubai.fermat_cht_core.layer.sup_app_module.chat.ChatSupAppModulePluginSubsystem;
import com.bitdubai.fermat_cht_core.layer.sup_app_module.identity.ChatSupAppIdentityModulePluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by franklin on 06/01/16.
 */
public class SupAppModuleLayer extends AbstractLayer {
    public SupAppModuleLayer() {
        super(Layers.SUB_APP_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new ChatActorCommunityModulePluginSubsystem());
            registerPlugin(new ChatSupAppIdentityModulePluginSubsystem());
            registerPlugin(new ChatSupAppModulePluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
