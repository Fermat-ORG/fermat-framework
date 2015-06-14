package com.bitdubai.fermat_api.layer.cry_2_crypto_vault;

import java.util.UUID;

/**
 * Created by rodrigo on 10/06/15.
 */
public interface CryptoVault {
    public void setUserId (UUID UserId);
    public UUID getUserId ();
    public Object getWallet();
}
