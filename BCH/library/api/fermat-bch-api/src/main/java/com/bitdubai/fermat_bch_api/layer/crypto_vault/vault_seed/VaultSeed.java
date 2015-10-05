package com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed;

import java.util.List;

/**
 * Created by rodrigo on 9/19/15.
 */
public interface VaultSeed {
    List<String> getMnemonicCode();
    long getCreationTimeSeconds();
    byte[] getSeedBytes();
}
