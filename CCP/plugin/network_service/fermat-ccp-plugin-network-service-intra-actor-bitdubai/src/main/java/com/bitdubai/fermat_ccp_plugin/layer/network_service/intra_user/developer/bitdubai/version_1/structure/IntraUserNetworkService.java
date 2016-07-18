package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;

import java.io.Serializable;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkService  implements IntraUserInformation,Serializable {


    private String publicKey;
    private byte[] profileImage;
    private String name;
private ProfileStatus state;
    private String phrase;

    public IntraUserNetworkService(String publicKey,byte[] profileImage,String name, String phrase, ProfileStatus state){
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.name = name;
        this.phrase = phrase;
        this.state = state;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public byte[] getProfileImage() {
        return (profileImage!=null) ? this.profileImage.clone() : null;
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

    @Override
    public ProfileStatus getState() {
        return this.state;
    }

    @Override
    public void setProfileImageNull() {
        profileImage = new byte[0];
    }

    @Override
    public long getContactRegistrationDate() {
        return 0;
    }
}
