package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkService  implements IntraUserInformation {


    private String publicKey;
    private byte[] profileImage;
    private String name;

    private String phrase;

    public IntraUserNetworkService(String publicKey,byte[] profileImage,String name, String phrase){
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.name = name;
        this.phrase = phrase;
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

    @Override
    public String getPhrase() {
        return this.phrase;
    }

    @Override
    public ConnectionState getConnectionState() {
        return null;
    }
}
