package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;

import java.math.BigDecimal;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 */
public class BrokerSubmitOfflineMerchandiseTransactionManager implements BrokerSubmitOfflineMerchandiseManager {

    @Override
    public void submitMerchandise(
            BigDecimal referencePrice,
            String cbpWalletPublicKey,
            String offlineWalletPublicKey,
            String contractHash) throws CantSubmitMerchandiseException {
        //TODO implement this
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        //TODO implement this
        return null;
    }
}
