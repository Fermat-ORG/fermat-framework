package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.ErrorManagerPlatformServiceAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.DeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Error Manager Platform Service Addon.
 *
 * Here we can choose between the different versions of the Error Manager Addon.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class DeveloperBitDubai extends AbstractAddonDeveloper {

    public DeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));


    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new ErrorManagerPlatformServiceAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
