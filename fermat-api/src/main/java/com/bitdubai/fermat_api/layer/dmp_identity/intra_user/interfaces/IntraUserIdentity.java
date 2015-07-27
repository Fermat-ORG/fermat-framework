package com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentity</code>
 * defines the methods related to the extraction of the information of an intra user
 */
public interface IntraUserIdentity {

    /**
     * The method <code>getAlias</code> returns the alias of the represented intra user
     *
     * @return the alias of the represented intra user
     */
    String getAlias();

    /**
     * The method <code>getPublicKey</code> returns the public key of the represented intra user
     * @return the public key of the represented intra user
     */
    String getPublicKey();
}
