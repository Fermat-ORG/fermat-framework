package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;

/**
 * Created by Yordin Alayn on 21.11.15.
 */
public interface CryptoCustomerActorRecord {

    String getActorPublicKey();

    String getActorName();

    Actors getActorType();

    byte[] getActorPhoto();

    String createMessageSignature(String message) throws CantSignExtraUserMessageException;
}
