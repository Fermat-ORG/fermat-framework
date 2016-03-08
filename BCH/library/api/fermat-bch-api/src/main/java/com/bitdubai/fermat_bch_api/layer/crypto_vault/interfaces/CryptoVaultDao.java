package com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;

/**
 * Created by rodrigo on 3/8/16.
 */
public interface CryptoVaultDao {

    int getPublicKeyPosition(String publicKey) throws CantExecuteDatabaseOperationException;
}
