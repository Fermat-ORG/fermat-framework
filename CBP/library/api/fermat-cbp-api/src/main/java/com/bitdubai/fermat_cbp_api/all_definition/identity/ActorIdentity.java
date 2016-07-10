package com.bitdubai.fermat_cbp_api.all_definition.identity;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;

import java.io.Serializable;


/**
 * Created by jorgegonzalez on 2015.09.18..
 */
public interface ActorIdentity extends Serializable {
    String getAlias();

    String getPublicKey();

    byte[] getProfileImage();

    void setNewProfileImage(final byte[] imageBytes);

    boolean isPublished();

    ExposureLevel getExposureLevel();

    String createMessageSignature(String message) throws CantCreateMessageSignatureException;

    long getAccuracy();

    GeoFrequency getFrequency();

}
