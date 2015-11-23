package com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantListCryptoAddressBookRecordsException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager</code>
 * indicates the functionality of a CryptoAddressBookManager.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/15.
 * @version 1.0
 */
public interface CryptoAddressBookManager extends FermatManager {

    /**
     * Throw the method <code>getCryptoAddressBookRecordByCryptoAddress</code> you can get an instance of the CryptoAddressBookRecord to which the
     * CryptoAddress belongs.
     *
     * @param cryptoAddress we're looking for
     * @return an instance of a CryptoAddressBookRecord
     * @throws CantGetCryptoAddressBookRecordException if something goes wrong
     * @throws CryptoAddressBookRecordNotFoundException if we can't found the CryptoAddressBook
     */
    CryptoAddressBookRecord getCryptoAddressBookRecordByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetCryptoAddressBookRecordException, CryptoAddressBookRecordNotFoundException;

    /**
     * Throw the method <code>listCryptoAddressBookRecordsByWalletPublicKey</code> you can list all the instances of CryptoAddressBookRecord that
     * belongs to the given wallet public key
     *
     * @param walletPublicKey public key of the wallet for which i'm looking crypto address book records.
     * @return a list of instances of CryptoAddressBookRecord
     * @throws CantListCryptoAddressBookRecordsException if something goes wrong
     */
    List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByWalletPublicKey(String walletPublicKey) throws CantListCryptoAddressBookRecordsException;

    /**
     * Throw the method <code>listCryptoAddressBookRecordsByDeliveredByActorPublicKey</code> you can list all the instances of CryptoAddressBookRecord that
     * belongs to the given actor public key
     *
     * @param deliveredByActorPublicKey public key of the actor for which i'm looking crypto address book records.
     * @return a list of instances of CryptoAddressBookRecord
     * @throws CantListCryptoAddressBookRecordsException if something goes wrong
     */
    List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredByActorPublicKey(String deliveredByActorPublicKey) throws CantListCryptoAddressBookRecordsException;

    /**
     * Throw the method <code>listCryptoAddressBookRecordsByDeliveredToActorPublicKey</code> you can list all the instances of CryptoAddressBookRecord that
     * belongs to the given actor public key
     *
     * @param deliveredToActorPublicKey public key of the actor for which i'm looking crypto address book records.
     * @return a list of instances of CryptoAddressBookRecord
     * @throws CantListCryptoAddressBookRecordsException if something goes wrong
     */
    List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredToActorPublicKey(String deliveredToActorPublicKey) throws CantListCryptoAddressBookRecordsException;

    /**
     * Throw the method <code>registerCryptoAddress</code> you can register a new address and create a new CryptoAddressBookRecord
     *
     * @param cryptoAddress you want to register
     * @param deliveredByActorPublicKey public key of the actor which is requesting the registering
     * @param deliveredByType type of the actor which is requesting the registering
     * @param deliveredToActorPublicKey public key of the actor with whom the actor is trying to register
     * @param deliveredToType type of the actor with whom the actor is trying to register
     * @param platform to which it belongs
     * @param vaultType to which it belongs
     * @param vaultIdentifier to which it belongs
     * @param walletPublicKey to which it belongs
     * @param walletType to which it belongs
     * @throws CantRegisterCryptoAddressBookRecordException if something goes wrong
     */
    void registerCryptoAddress(CryptoAddress cryptoAddress,
                               String deliveredByActorPublicKey,
                               Actors deliveredByType,
                               String deliveredToActorPublicKey,
                               Actors deliveredToType,
                               Platforms platform,
                               VaultType vaultType,
                               String vaultIdentifier,
                               String walletPublicKey,
                               ReferenceWallet walletType) throws CantRegisterCryptoAddressBookRecordException;

}
