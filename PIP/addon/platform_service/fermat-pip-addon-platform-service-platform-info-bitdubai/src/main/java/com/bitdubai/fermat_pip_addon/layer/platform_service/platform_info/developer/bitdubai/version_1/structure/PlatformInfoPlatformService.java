package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo;

/**
 * Created by natalia on 29/07/15.
 */
public class PlatformInfoPlatformService implements PlatformInfo {

    private String platformVersion = "Versión = 1.0.0";

    @Override
    public String getVersion() {
        return this.platformVersion;
    }
}
