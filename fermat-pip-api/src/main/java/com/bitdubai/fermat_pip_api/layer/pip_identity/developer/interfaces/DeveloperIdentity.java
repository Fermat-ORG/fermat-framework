package com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces;

/**
 * This interface let you access to the Developer public Information
 *
 * @author Ezequiel Postan
 */
public interface DeveloperIdentity {

    /**
     *
     * @return the alias of the represented developer identity
     */
    String getAlias();

    /**
     *
     * @return the public key of the represented developer
     */
    String getPublicKey();
}
