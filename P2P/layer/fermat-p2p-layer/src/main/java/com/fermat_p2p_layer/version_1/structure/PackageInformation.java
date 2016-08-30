package com.fermat_p2p_layer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;

/**
 * Created by mati on 24/08/16.
 */
public class PackageInformation {

    private NetworkServiceType networkServiceType;
    private PackageType packageType;

    public PackageInformation(NetworkServiceType networkServiceType, PackageType packageType) {
        this.networkServiceType = networkServiceType;
        this.packageType = packageType;
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    @Override
    public String toString() {
        return "PackageInformation{" +
                "networkServiceType=" + networkServiceType +
                ", packageType=" + packageType +
                '}';
    }
}
