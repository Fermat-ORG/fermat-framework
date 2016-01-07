package com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;

/**
 * Created by rodrigo on 1/5/16.
 */
public class WatchOnlyVaultExtendedPublicKey{
    private final String fileName;
    private final String directoryName;
    private final ExtendedPublicKey extendedPublicKey;

    public WatchOnlyVaultExtendedPublicKey(String fileName, String directoryName, ExtendedPublicKey extendedPublicKey) {
        this.fileName = fileName;
        this.directoryName = directoryName;
        this.extendedPublicKey = extendedPublicKey;
    }

    public String getFilename() {
        return this.fileName;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }

    public ExtendedPublicKey getExtendedPublicKey() {
        return this.extendedPublicKey;
    }
}
