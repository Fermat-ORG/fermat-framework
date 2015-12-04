package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions.CantCloseContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/12/15.
 */
public class CloseContractTransactionManager implements CloseContractManager {


    @Override
    public void openSaleContract(String contractHash) throws CantCloseContractException {

    }

    @Override
    public void openPurchaseContract(String contractHash) throws CantCloseContractException {

    }

    @Override
    public ContractTransactionStatus getOpenContractStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        return null;
    }
}
