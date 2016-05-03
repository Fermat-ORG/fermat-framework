package com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BrokerSubmitMerchandiseManager;

import java.math.BigDecimal;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerSubmitOfflineMerchandiseManager extends BrokerSubmitMerchandiseManager {

    /**
     * This method send a payment according the contract elements.
     *
     * @param referencePrice
     * @param cbpWalletPublicKey
     * @param offlineWalletPublicKey
     * @param contractHash
     * @param merchandiseMoneyType
     * @param merchandiseCurrency
     */
    void submitMerchandise(BigDecimal referencePrice,
                           String cbpWalletPublicKey,
                           String offlineWalletPublicKey,
                           String contractHash,
                           MoneyType merchandiseMoneyType,
                           FiatCurrency merchandiseCurrency) throws CantSubmitMerchandiseException;

    /**
     * This method send a payment according the contract clauses.
     * In this case, this method submit merchandise and not requires the cbpWalletPublicKey,
     * this public key can be obtained from the crypto broker wallet
     *
     * @param referencePrice
     * @param cbpWalletPublicKey
     * @param contractHash
     */
    void submitMerchandise(BigDecimal referencePrice, String cbpWalletPublicKey, String contractHash) throws CantSubmitMerchandiseException;


}
