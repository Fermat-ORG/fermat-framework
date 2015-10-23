package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;

/**
 * Created by eze on 12/06/15.

 * The purpose of this class is to indicate the correct
 * destination for a given transaction
 */
public class SpecialistSelector implements DealsWithCryptoAddressBook {

    /*
     * DealsWithCryptoAddressBook Interface member variables
     */
    private CryptoAddressBookManager cryptoAddressBookManager;

    /*
     * DealsWithActorAddressBook Interface method implementation
     */
    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    public Specialist getSpecialist(CryptoTransaction cryptoTransaction) throws CantSelectSpecialistException {

        CryptoAddress cryptoAddress = new CryptoAddress();
        //todo si la direccion del to es vacia, poner la del from
        cryptoAddress.setAddress(cryptoTransaction.getAddressTo().getAddress());
        cryptoAddress.setCryptoCurrency(cryptoTransaction.getCryptoCurrency());

        try {
            CryptoAddressBookRecord cryptoAddressBookRecord = cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(cryptoAddress);
            switch (cryptoAddressBookRecord.getDeliveredToActorType()) {
                case DEVICE_USER:
                    return Specialist.DEVICE_USER_SPECIALIST;
                case INTRA_USER:
                    return Specialist.INTRA_USER_SPECIALIST;
                case EXTRA_USER:
                    return Specialist.EXTRA_USER_SPECIALIST;
                case DAP_ASSET_ISSUER:
                    return Specialist.ASSET_ISSUER_SPECIALIST;
                default:
                    // Here we have a serious problem
                    throw new CantSelectSpecialistException("NO SPECIALIST FOUND",null,"Actor: " + cryptoAddressBookRecord.getDeliveredToActorType() + " with code " + cryptoAddressBookRecord.getDeliveredToActorType().getCode(),"Actor not considered in switch statement");
            }

        } catch (CantGetCryptoAddressBookRecordException|CryptoAddressBookRecordNotFoundException e) {
            // todo ver como manejar CryptoAddressBookRecordNotFoundException
            // This exception will be managed by the relay agent
            throw new CantSelectSpecialistException("Can't get actor address from registry", e,"CryptoAddress: "+ cryptoAddress.getAddress(),"Address not stored");
        }
    }
}
