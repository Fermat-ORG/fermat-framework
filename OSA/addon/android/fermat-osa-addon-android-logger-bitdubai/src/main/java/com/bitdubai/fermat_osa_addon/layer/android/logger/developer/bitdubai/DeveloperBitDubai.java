package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.LoggerSystemAddonRoot;

/**
 * Created by rodrigo on 2015.06.25..
 * Modified by lnacosta (laion.cj91@gmail.com) on //.
 */
public class DeveloperBitDubai extends AbstractAddonDeveloper {

    public DeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new LoggerSystemAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
