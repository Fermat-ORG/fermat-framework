package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantGetHoldTransactionException;

import java.util.UUID;

/**
 * TODO description
 *
 * Created by Franklin Marcano on 21/11/2015.
 */
public interface CryptoHoldTransactionManager extends FermatManager {

    /**
     * Through this method you would be able to TODO complete
     *
     * @param holdParameters TODO complete
     *
     * @return an instance of CryptoHoldTransaction.
     *
     * @throws CantCreateHoldTransactionException if something goes wrong.
     */
    CryptoHoldTransaction   createCryptoHoldTransaction(CryptoHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException;

    /**
     * Through this method you would be able to get TODO complete
     *
     * @param transactionId fermat id of the transaction.
     *
     * @return an element of the enum CryptoTransactionStatus indicating the status of the hold.
     *
     * @throws CantGetHoldTransactionException if something goes wrong.
     */
    CryptoTransactionStatus getCryptoHoldTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException;

}
