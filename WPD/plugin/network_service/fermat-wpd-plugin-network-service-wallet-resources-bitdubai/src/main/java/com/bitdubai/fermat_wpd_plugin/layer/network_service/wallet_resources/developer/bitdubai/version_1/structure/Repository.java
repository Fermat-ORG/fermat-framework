package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure;

/**
 * Created by Matias Furszyfer on 2015.08.03..
 */
public class Repository {

    private String path;

    private String skinName;

    private String navigationStructureVersion;

    public Repository(String skinName, String navigationStructureVersion, String path) {
        this.skinName = skinName;
        this.navigationStructureVersion = navigationStructureVersion;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getNavigationStructureVersion() {
        return navigationStructureVersion;
    }

    public String getSkinName() {
        return skinName;
    }


}
