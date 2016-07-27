package com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed;

import java.util.List;

/**
 * Created by rodrigo on 7/4/16.
 */
public class CryptoVaultSeed implements VaultSeed {
    private final List<String> mNemonicCode;
    private final long creationTimeSeconds;
    private final byte[] seedBytes;

    public CryptoVaultSeed(List<String> mNemonicCode, long creationTimeSeconds, byte[] seedBytes) {
        this.mNemonicCode = mNemonicCode;
        this.creationTimeSeconds = creationTimeSeconds;
        this.seedBytes = seedBytes;
    }

    @Override
    public List<String> getMnemonicCode() {
        return this.mNemonicCode;
    }

    /**
     * The Mnemonic Code in a single String.
     * @return
     */
    public String getMnemonicPhrase() {
        StringBuilder builder = new StringBuilder();
        for (String word : this.mNemonicCode){
            builder.append(word);
            builder.append(" ");
        }

        return builder.toString();
    }

    @Override
    public long getCreationTimeSeconds() {
        return this.creationTimeSeconds;
    }

    @Override
    public byte[] getSeedBytes() {
        return this.getSeedBytes();
    }
}
