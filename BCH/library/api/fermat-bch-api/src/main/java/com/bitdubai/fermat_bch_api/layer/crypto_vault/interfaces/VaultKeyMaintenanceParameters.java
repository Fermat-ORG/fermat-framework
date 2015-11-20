package com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces;

/**
 * Created by rodrigo on 10/4/15.
 */
public interface VaultKeyMaintenanceParameters {
    /**
     * When this amount of unused keys is reached, new keys will be generated.
     */
    public static final int KEY_PERCENTAGE_GENERATION_THRESHOLD = 30;

    /**
     * When new keys are being generated, how many keys we are creating each time
     */
    public static final int KEY_GENERATION_BLOCK = 100;

    /**
     * When true, we save all the detailed keys information into database
     */
    public static final boolean STORE_DETAILED_KEY_INFORMATION = true;
}
