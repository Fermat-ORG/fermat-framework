package com.bitdubai.fermat_cbp_api.all_definition.identity;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.IdentityPublished;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

/**
 * Created by jorgegonzalez on 2015.09.18..
 */
public interface ActorIdentity {
    String getAlias();

    String getPublicKey();

    byte[] getProfileImage();

    void setNewProfileImage(final byte[] imageBytes);

    boolean isPublished();

    ExposureLevel getExposureLevel();

    String createMessageSignature(String message) throws CantCreateMessageSignatureException;
}
