package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.vault_seed;

import java.util.List;

/**
 * Created by rodrigo on 9/19/15.
 */
public interface VaultSeed {
    List<String> getMnemonicCode();
    long getCreationTimeSeconds();
    byte[] getSeedBytes();
}
