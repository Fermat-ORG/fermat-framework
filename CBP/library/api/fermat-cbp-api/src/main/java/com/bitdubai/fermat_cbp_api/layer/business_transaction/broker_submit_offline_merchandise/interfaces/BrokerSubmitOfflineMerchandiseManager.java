package com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BrokerSubmitMerchandiseManager;

import java.math.BigDecimal;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/12/15.
 */
public interface BrokerSubmitOfflineMerchandiseManager extends BrokerSubmitMerchandiseManager {

    /**
     * This method send a payment according the contract elements.
     * @param referencePrice
     * @param cbpWalletPublicKey
     * @param offlineWalletPublicKey
     * @param contractHash
     */
    void submitMerchandise(BigDecimal referencePrice, String cbpWalletPublicKey, String offlineWalletPublicKey, String contractHash)throws CantSubmitMerchandiseException;


}
