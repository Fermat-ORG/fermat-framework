package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.PhotoType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkService  implements IntraUserInformation {


    private String publicKey;
    private byte[] profileImage;
    private String name;

    private String phrase;
    private PhotoType photoType;

    public IntraUserNetworkService(String publicKey,byte[] profileImage,String name, String phrase, PhotoType photoType){
        this.publicKey = publicKey;
        this.profileImage = profileImage;
        this.name = name;
        this.phrase = phrase;
        this.photoType = photoType;
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
    public PhotoType getPhotoType() {
        return this.photoType;
    }

    @Override
    public ConnectionState getConnectionState() {
        return null;
    }
}
