package com.bitdubai.fermat_api.layer.desktop;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;

/**
 * Created by mati on 2015.11.20..
 */
public class InstalledDesktop implements FermatApp {

    String publicKey;
    String dekstopName;

    @Override
    public String getAppName() {
        return dekstopName;
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }

    @Override
    public AppsStatus getAppStatus() {
        return null;
    }

    @Override
    public FermatAppType getAppType() {
        return FermatAppType.DESKTOP;
    }
//
//    @Override
//    public AppStructureType getAppStructureType() {
//        return AppStructureType.REFERENCE;
//    }

    @Override
    public byte[] getAppIcon() {
        return new byte[0];
    }

    @Override
    public int getIconResource() {
        return 0;
    }

    @Override
    public void setBanner(int res) {

    }

    @Override
    public int getBannerRes() {
        return 0;
    }
}
