package com.bitdubai.fermat_ccp_core.test_classes;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;

/**
 * The class <code>com.bitdubai.fermat_ccp_core.test_classes.CCPPluginIdsManager</code>
 * haves all the functionality to manage CPP Plugin ids.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class CCPPluginIdsManager extends PluginIdsManager {

    public CCPPluginIdsManager(final PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {
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
