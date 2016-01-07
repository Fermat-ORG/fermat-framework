package com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;

/**
 * Created by rodrigo on 1/5/16.
 */
class WatchOnlyVaultExtendedPublicKey{
    final String FILE_NAME;
    final String DIRECTORY_NAME;
    final ExtendedPublicKey extendedPublicKey;

    public WatchOnlyVaultExtendedPublicKey(String FILE_NAME, String DIRECTORY_NAME, ExtendedPublicKey extendedPublicKey) {
        this.FILE_NAME = FILE_NAME;
        this.DIRECTORY_NAME = DIRECTORY_NAME;
        this.extendedPublicKey = extendedPublicKey;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public String getDIRECTORY_NAME() {
        return DIRECTORY_NAME;
    }

    public ExtendedPublicKey getExtendedPublicKey() {
        return extendedPublicKey;
    }
}
