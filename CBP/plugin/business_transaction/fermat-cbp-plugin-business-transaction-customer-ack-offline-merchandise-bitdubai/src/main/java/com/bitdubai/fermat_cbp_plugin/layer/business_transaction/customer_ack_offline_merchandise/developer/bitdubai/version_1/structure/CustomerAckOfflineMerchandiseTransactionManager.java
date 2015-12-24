package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/12/15.
 */
public class CustomerAckOfflineMerchandiseTransactionManager implements CustomerAckOfflineMerchandiseManager {
    @Override
    public void ackMerchandise(String walletPublicKey, String contractHash) throws CantAckMerchandiseException {

    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        return null;
    }
}
