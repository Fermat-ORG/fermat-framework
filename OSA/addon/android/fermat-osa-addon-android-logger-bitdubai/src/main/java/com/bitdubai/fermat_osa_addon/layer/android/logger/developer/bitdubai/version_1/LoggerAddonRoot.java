package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerManager;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class LoggerAddonRoot extends AbstractAddon {

    public LoggerAddonRoot(AddonVersionReference addonVersionReference) {
        super(addonVersionReference);
    }

    private LoggerManager loggerManager;

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I initialize the logger Manager
         */
        loggerManager = new LoggerManager();

        super.start();
    }

    @Override
    public FermatManager getManager() {
        return loggerManager;
    }

}
