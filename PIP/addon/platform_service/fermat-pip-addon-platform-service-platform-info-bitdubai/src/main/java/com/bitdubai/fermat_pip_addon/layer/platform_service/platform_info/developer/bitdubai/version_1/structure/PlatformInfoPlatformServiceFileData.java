package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

/**
 * Created by rodrigo on 8/5/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformInfoPlatformServiceFileData implements PlatformInfo {

    private final String jdk;
    private ScreenSize screenSize;
    private final Version version;

    public PlatformInfoPlatformServiceFileData() {

        this.jdk = "1.7";
        this.version = new Version();
    }

    public PlatformInfoPlatformServiceFileData(final ScreenSize screenSize) {

        this.jdk = "1.7";
        this.screenSize = screenSize;
        this.version = new Version();
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public ScreenSize getScreenSize() {
        return screenSize;
    }

    @Override
    public String getJdk() {
        return jdk;
    }

    @Override
    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

}
