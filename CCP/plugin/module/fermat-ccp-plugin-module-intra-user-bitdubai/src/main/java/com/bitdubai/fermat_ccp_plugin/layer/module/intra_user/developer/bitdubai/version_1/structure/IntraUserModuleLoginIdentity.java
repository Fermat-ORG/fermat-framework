package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleLoginIdentity</code>
 * is the implementation of IntraUserLoginIdentity interface.
 * And provides the methods to get the information of an identity a user can use to log in..
 *
 * Created by natalia on 11/08/15.
 */
public class IntraUserModuleLoginIdentity implements IntraUserLoginIdentity {

    private String alias;
    private String publicKey;
    private byte[] profileImage;


    /**
     * Constructor
     */

    public IntraUserModuleLoginIdentity(String alias,String publicKey,byte[] profileImage)
    {
        this.alias = alias;
        this.publicKey = publicKey;
        this.profileImage = (byte[] )profileImage.clone();
    }
    /**
     * That method returns the alias of the intra user identity
     *
     * @return the alias of the intra user
     */
    @Override
    public String getAlias() {
        return this.alias;
    }

    /**
     * That method returns the public key of the intra user identity
     *
     * @return the public key of the intra user
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }


    /**
     * That method returns the profile image of the intra user identity
     *
     * @return the profile image of the intra user
     */
    @Override
    public byte[] getProfileImage() {
        return (byte[] )this.profileImage.clone();
    }
}
