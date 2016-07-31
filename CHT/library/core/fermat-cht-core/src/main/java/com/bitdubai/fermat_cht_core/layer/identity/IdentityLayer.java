package com.bitdubai.fermat_cht_core.layer.identity;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cht_core.layer.identity.chat.ChatIdentityPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by franklin on 10/01/16.
 */
public class IdentityLayer extends AbstractLayer {

    public IdentityLayer() {
        super(Layers.IDENTITY);
    }

    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new ChatIdentityPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
