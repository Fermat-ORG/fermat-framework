package com.bitdubai.fermat_dap_api.layer.dap_actor;

import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantSignExtraUserMessageException;

/**
 * Created by Nerio on 10/09/15.
 */
public interface DAPActor {

    String getActorUserPublicKey();

    String getActorIssuerPublicKey();

    String getActorRedeemPointPublicKey();

    String getName();

    byte[] getPhoto();

    String createMessageSignature(String message) throws CantSignExtraUserMessageException;
}
