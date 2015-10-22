package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleInformation</code>
 * is the implementation of IntraUserInformation interface.
 * And provides the method to extract information about an intra user.
 *
 * Created by natalia on 11/08/15.
 */
public class IntraUserModuleInformation implements IntraUserInformation {

    private String name;
    private String publicKey;
    private byte[] profileImage;


    public IntraUserModuleInformation(String name,String publicKey,byte[] profileImage)
    {
        this.name = name;
        this.publicKey = publicKey;
        //this.profileImage = (byte[] )profileImage.clone();
    }
    /**
     * That method returns the public key of the represented Intra User
     * @return the public key of the intra user
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * That method returns the name of the represented intra user
     *
     * @return the name of the intra user
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * That method returns the profile image of the represented intra user
     *
     * @return the profile image
     */
    @Override
    public byte[] getProfileImage() {
        return (profileImage!=null) ? (byte[] )this.profileImage.clone() : null;
    }
}
