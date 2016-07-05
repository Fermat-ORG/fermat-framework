package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.interfaces;

/**
 * Created by rodrigo on 10/4/15.
 */
public interface VaultKeyMaintenanceParameters {
    /**
     * When this amount of unused keys is reached, new keys will be generated.
     */
    int KEY_PERCENTAGE_GENERATION_THRESHOLD = 30;

    /**
     * When new keys are being generated, how many keys we are creating each time
     */
    int KEY_GENERATION_BLOCK = 200;

    /**
     * When true, we save all the detailed keys information into database
     */
    boolean STORE_DETAILED_KEY_INFORMATION = true;
}
