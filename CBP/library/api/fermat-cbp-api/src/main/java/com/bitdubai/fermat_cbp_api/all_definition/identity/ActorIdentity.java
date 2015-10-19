package com.bitdubai.fermat_cbp_api.all_definition.identity;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.IdentityPublished;

/**
 * Created by jorgegonzalez on 2015.09.18..
 */
public interface ActorIdentity {
    String getAlias();

    String getPublicKey();

    byte[] getProfileImage();

    void setNewProfileImage(final byte[] imageBytes);

    boolean isPublished();

    String createMessageSignature(String message) throws CantCreateMessageSignatureException;
}
