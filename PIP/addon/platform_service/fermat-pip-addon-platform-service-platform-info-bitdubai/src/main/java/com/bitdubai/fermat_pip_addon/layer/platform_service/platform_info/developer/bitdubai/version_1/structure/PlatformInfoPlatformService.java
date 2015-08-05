package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo;

/**
 * Created by natalia on 29/07/15.
 */
public class PlatformInfoPlatformService implements PlatformInfo {

    private Version platformVersion = new Version ("1.0.0");
    public static ScreenSize screenSize = ScreenSize.MEDIUM; //defaults to Medium

    @Override
    public Version getVersion() {
        return this.platformVersion;
    }

    @Override
    public ScreenSize getScreenSize() {
        return screenSize;
    }

    @Override
    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }
}
