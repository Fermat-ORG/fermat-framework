package com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.exceptions.CantInitializeWatchOnlyVaultException;

/**
 * Created by rodrigo on 12/30/15.
 */
public interface WatchOnlyVaultManager extends FermatManager, PlatformCryptoVault {
    /**
     * Will initialize the Vault by deriving the keys, starting the agents and start monitoring the network
     * @param extendedPublicKey
     * @throws CantInitializeWatchOnlyVaultException
     */
    void initialize (ExtendedPublicKey extendedPublicKey) throws CantInitializeWatchOnlyVaultException;
}
