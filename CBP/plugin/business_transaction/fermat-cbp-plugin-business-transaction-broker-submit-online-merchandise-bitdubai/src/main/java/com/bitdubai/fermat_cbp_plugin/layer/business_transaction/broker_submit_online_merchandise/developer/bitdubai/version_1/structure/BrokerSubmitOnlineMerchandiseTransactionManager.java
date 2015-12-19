package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/12/15.
 */
public class BrokerSubmitOnlineMerchandiseTransactionManager implements BrokerSubmitOnlineMerchandiseManager {
    @Override
    public void submitMerchandise(String walletPublicKey, String contractHash) throws CantSubmitMerchandiseException {
        //TODO: to implement
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        //TODO: to implement
        return null;
    }
}
