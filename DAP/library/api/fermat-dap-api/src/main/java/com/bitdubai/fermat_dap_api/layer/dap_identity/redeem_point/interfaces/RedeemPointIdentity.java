package com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantSingMessageException;

/**
 * Created by Nerio on 07/09/15.
 */
public interface RedeemPointIdentity {


    /**
     * Get the alias of the represented translator identity
     *
     * @return String Alias
     */
    public String getAlias();

    /**
     * Get the public key of the represented developer
     *
     * @return string pulic key
     */
    public String getPublicKey();

    /**
     * Sign a message with translator private key
     *
     * @param mensage to sign
     * @return string signed message
     * @throws CantSingMessageException
     */
    public String createMessageSignature(String mensage) throws CantSingMessageException;
}
