package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.io.Serializable;


/**
 * Created by rodrigo on 8/5/15.
 */
public class PlatformInfo implements com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo,Serializable{
    final Version version = new Version(1,0,0);
    ScreenSize screenSize;
    final String jdk = "1.7";


    public Version getVersion() {
        return version;
    }

   public ScreenSize getScreenSize() {
        return screenSize;
    }

    @Override
    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public String getJdk() {
        return jdk;
    }
}
