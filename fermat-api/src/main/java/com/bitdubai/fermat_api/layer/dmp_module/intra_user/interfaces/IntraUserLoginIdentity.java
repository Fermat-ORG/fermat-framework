package com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserLoginIdentity</code>
 * provides the methods to get the information of an identity a user can use to log in.
 */
public interface IntraUserLoginIdentity {



    /**
     * The method  <code>getAlias</code> returns the alias of the intra user identity
     *
     * @return the alias of the intra user
     */
    String getAlias();

    /**
     * The method  <code>getPublicKey</code> returns the public key of the intra user identity
     *
     * @return the public key of the intra user
     */
    String getPublicKey();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the intra user identity
     *
     * @return the profile image of the intra user
     */
    byte[] getProfileImage();

}
