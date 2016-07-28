package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerManager;

/**
 * Created by rodrigo on 2015.06.25..
 * Modified by lnacosta (laion.cj91@gmail.com) on 05/11/2015.
 */
public final class LoggerSystemAddonRoot extends AbstractAddon {

    private FermatManager fermatManager;

    public LoggerSystemAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            fermatManager = new LoggerManager();

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Logger System Manager starting.", "Unhandled Exception trying to start the Logger System manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }

}
