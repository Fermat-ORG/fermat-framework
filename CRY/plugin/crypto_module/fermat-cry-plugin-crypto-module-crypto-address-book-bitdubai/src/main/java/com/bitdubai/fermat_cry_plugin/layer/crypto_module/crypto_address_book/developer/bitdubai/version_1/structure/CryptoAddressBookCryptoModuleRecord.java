package com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.InvalidCryptoAddressBookRecordParametersException;

/**
 * The class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure.CryptoAddressBookCryptoModuleRecord</code>
 * represents a crypto address book record.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 * @version 1.0
 */
public class CryptoAddressBookCryptoModuleRecord implements CryptoAddressBookRecord {

    CryptoAddress cryptoAddress;

    String deliveredByActorPublicKey;

    Actors deliveredByActorType;

    String deliveredToActorPublicKey;

    Actors deliveredToActorType;

    Platforms platform;

    VaultType vaultType;

    String vaultIdentifier;

    String walletPublicKey;

    ReferenceWallet walletType;

    public CryptoAddressBookCryptoModuleRecord(CryptoAddress cryptoAddress,
                                               String deliveredByActorPublicKey,
                                               Actors deliveredByActorType,
                                               String deliveredToActorPublicKey,
                                               Actors deliveredToActorType,
                                               Platforms platform,
                                               VaultType vaultType,
                                               String vaultIdentifier,
                                               String walletPublicKey,
                                               ReferenceWallet walletType) throws InvalidCryptoAddressBookRecordParametersException {
        if (cryptoAddress == null ||
                deliveredByActorPublicKey == null ||
                deliveredByActorType == null ||
                deliveredToActorPublicKey == null ||
                deliveredToActorType == null ||
                platform == null ||
                vaultType == null ||
                vaultIdentifier == null ||
                walletPublicKey == null ||
                walletType == null)
            throw new InvalidCryptoAddressBookRecordParametersException();

        this.cryptoAddress = cryptoAddress;
        this.deliveredByActorPublicKey = deliveredByActorPublicKey;
        this.deliveredByActorType = deliveredByActorType;
        this.deliveredToActorPublicKey = deliveredToActorPublicKey;
        this.deliveredToActorType = deliveredToActorType;
        this.platform = platform;
        this.vaultType = vaultType;
        this.vaultIdentifier = vaultIdentifier;
        this.walletPublicKey = walletPublicKey;
        this.walletType = walletType;
    }

    @Override
    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    @Override
    public String getDeliveredByActorPublicKey() {
        return deliveredByActorPublicKey;
    }

    @Override
    public Actors getDeliveredByActorType() {
        return deliveredByActorType;
    }

    @Override
    public String getDeliveredToActorPublicKey() {
        return deliveredToActorPublicKey;
    }

    @Override
    public Actors getDeliveredToActorType() {
        return deliveredToActorType;
    }

    @Override
    public Platforms getPlatform() {
        return platform;
    }

    @Override
    public VaultType getVaultType() {
        return vaultType;
    }

    @Override
    public String getVaultIdentifier() {
        return vaultIdentifier;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public ReferenceWallet getWalletType() {
        return walletType;
    }
}