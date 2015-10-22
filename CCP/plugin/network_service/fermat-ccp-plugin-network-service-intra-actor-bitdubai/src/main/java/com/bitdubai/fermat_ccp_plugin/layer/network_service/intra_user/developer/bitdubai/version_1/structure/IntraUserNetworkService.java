package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkService  implements IntraUserInformation {


    private String publicKey;
    private byte[] profileImage;
    private String name;

    public IntraUserNetworkService(String publicKey,byte[] profileImage,String name){
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.name = name;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return (profileImage!=null) ? (byte[] )this.profileImage.clone() : null;
    }

    @Override
    public String getName() {
        return this.name;
    }



}
