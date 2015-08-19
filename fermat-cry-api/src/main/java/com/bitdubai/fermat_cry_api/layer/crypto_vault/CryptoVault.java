package com.bitdubai.fermat_cry_api.layer.crypto_vault;

import java.util.UUID;

/**
 * Created by rodrigo on 10/06/15.
 */
public interface CryptoVault {
    public void setUserPublicKey (String userPublicKey);
    public String getUserPublicKey ();
    public Object getWallet();
}
