package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public class CustomerAckOnlineMerchandiseTransactionManager implements CustomerAckOnlineMerchandiseManager {
    @Override
    public void ackMerchandise(
            String walletPublicKey,
            String contractHash) throws CantAckMerchandiseException {
        //TODO: implement this
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        return null;
    }
}
