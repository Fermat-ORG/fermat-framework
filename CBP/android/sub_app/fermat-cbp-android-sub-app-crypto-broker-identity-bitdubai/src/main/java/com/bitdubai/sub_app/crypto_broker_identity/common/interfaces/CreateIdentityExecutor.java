package com.bitdubai.sub_app.crypto_broker_identity.common.interfaces;

/**
 * Created by nelson on 14/10/15.
 */
public abstract class CreateIdentityExecutor {
    public static final int EXCEPTION_THROWN = 3;
    public static int SUCCESS = 1;
    public static int INVALID_ENTRY_DATA = 2;

    protected byte[] imageInBytes;
    protected String identityName;

    public CreateIdentityExecutor(byte[] imageInBytes, String identityName) {
        this.imageInBytes = imageInBytes;
        this.identityName = identityName;
    }

    public abstract int createNewIdentity();

    public void setImageInBytes(byte[] imageInBytes){
        this.imageInBytes = imageInBytes;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }
}
