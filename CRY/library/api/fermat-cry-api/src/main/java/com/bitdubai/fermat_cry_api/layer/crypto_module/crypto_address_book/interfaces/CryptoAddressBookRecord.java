package com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * The interface <code>com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord</code>
 * indicates the functionality of a CryptoAddressBookRecord.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/15.
 * @version 1.0
 */
public interface CryptoAddressBookRecord {

    /**
     * returns the crypto address to which it belongs
     * @return CryptoAddress instance.
     */
    CryptoAddress getCryptoAddress();

    /**
     * returns the public key of the delivered BY actor to which it belongs
     * @return String public key
     */
    String getDeliveredByActorPublicKey();

    /**
     * returns the actor type of the delivered BY actor
     * @return actors enum
     */
    Actors getDeliveredByActorType();

    /**
     * returns the public key of the delivered TO actor to which it belongs
     * @return String public key
     */
    String getDeliveredToActorPublicKey();

    /**
     * returns the actor type of the delivered TO actor
     * @return actors enum
     */
    Actors getDeliveredToActorType();

    /**
     * returns the platform to which it belongs
     * @return Platforms enum
     */
    Platforms getPlatform();

    /**
     * returns the type of vault to which it belongs
     * @return VaultType enum
     */
    VaultType getVaultType();

    /**
     * returns the identifier of the vault to which it belongs
     * @return String enum
     */
    String getVaultIdentifier();

    /**
     * returns the public key of the wallet to which it belongs
     * @return String public key
     */
    String getWalletPublicKey();

    /**
     * returns the reference wallet type to which it belongs
     * @return ReferenceWallet enum
     */
    ReferenceWallet getWalletType();
}
