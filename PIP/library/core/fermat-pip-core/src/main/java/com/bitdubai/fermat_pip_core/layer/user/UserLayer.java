package com.bitdubai.fermat_pip_core.layer.user;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_pip_core.layer.user.device_user.DeviceUserSubsystem;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.UserLayer</code>
 * contains all the necessary business logic to start the User Layer of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/11/2015.
 */
public class UserLayer extends AbstractLayer {

    public UserLayer() {
        super(Layers.USER);
    }

    public void start() throws CantStartLayerException {

        try {

            registerAddon(new DeviceUserSubsystem());

        } catch (CantRegisterAddonException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
