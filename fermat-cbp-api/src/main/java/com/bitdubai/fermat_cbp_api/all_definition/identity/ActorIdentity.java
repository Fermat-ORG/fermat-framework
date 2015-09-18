package com.bitdubai.fermat_cbp_api.all_definition.identity;

/**
 * Created by jorgegonzalez on 2015.09.18..
 */
public interface ActorIdentity {
    String getAlias();

    String getPublicKey();

    byte[] getProfileImage();

    void setNewProfileImage(final byte[] imageBytes);

    /*
     * TODO add exception
     */
    String createMessageSignature(String message);
}
