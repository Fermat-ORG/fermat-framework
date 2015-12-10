package com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces;

import java.util.UUID;

/**
 * Created by rodrigo on 10/06/15.
 */
public interface CryptoVault {
    public void setUserPublicKey (String userPublicKey);
    public String getUserPublicKey ();
    public Object getWallet();
}
