package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.seed_management;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * Created by rodrigo on 7/14/16.
 */
public class ImportedSeed {
    private final long importedSeedDate;
    private final CryptoAddress cryptoAddress;
    private BlockchainNetworkType blockchainNetworkType;
    private long balance;
    private ImportSeedProgress progress;

    /**
     * constructor
     * @param importedSeedDate
     * @param cryptoAddress
     */
    public ImportedSeed(long importedSeedDate, CryptoAddress cryptoAddress) {
        this.importedSeedDate = importedSeedDate;
        this.cryptoAddress = cryptoAddress;

        this.progress = ImportSeedProgress.NOT_STARTED;
    }

    public ImportSeedProgress getProgress() {
        return progress;
    }

    public void setProgress(ImportSeedProgress progress) {
        this.progress = progress;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getImportedSeedDate() {
        return importedSeedDate;
    }

    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "ImportedSeed{" +
                "importedSeedDate=" + importedSeedDate +
                ", cryptoAddress=" + cryptoAddress +
                ", blockchainNetworkType=" + blockchainNetworkType +
                ", balance=" + balance +
                ", progress=" + progress +
                '}';
    }
}
