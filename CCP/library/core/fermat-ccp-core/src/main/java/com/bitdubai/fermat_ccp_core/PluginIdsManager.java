package com.bitdubai.fermat_ccp_core;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginIdsManager;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPluginIdsManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.PluginIdsManager</code>
 * haves all the functionality to manage CPP Plugin ids.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class PluginIdsManager extends AbstractPluginIdsManager {

    public PluginIdsManager(final PlatformFileSystem platformFileSystem) throws CantStartPluginIdsManagerException {
        super(platformFileSystem);
    }

    @Override
    protected FermatPluginsEnum getPluginByKey(String key) throws InvalidParameterException {
        return CCPPlugins.getByCode(key);
    }

    @Override
    protected FermatPluginsEnum[] getAllPlugins() {
        return CCPPlugins.values();
    }

    @Override
    protected Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

}
