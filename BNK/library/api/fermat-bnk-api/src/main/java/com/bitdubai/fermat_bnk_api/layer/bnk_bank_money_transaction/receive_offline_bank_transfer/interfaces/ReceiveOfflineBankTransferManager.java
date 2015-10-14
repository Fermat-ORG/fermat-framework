package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.exceptions.CantCreateReceiveOfflineBankTransferException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.exceptions.CantGetReceiveOfflineBankTransferException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.exceptions.CantUpdateStatusReceiveOfflineBankTransferException;

import java.util.List;
import java.util.UUID;

/**
 * Created by yordin on 25/09/15.
 */
public interface ReceiveOfflineBankTransferManager {

    List<ReceiveOfflineBankTransfer> getAllReceiveOfflineBankTransferFromCurrentDeviceUser() throws CantGetReceiveOfflineBankTransferException;

    ReceiveOfflineBankTransfer createReceiveOfflineBankTransfer(
         final String publicKeyBroker
        ,final String publicKeyCustomer
        ,final String balanceType
        ,final String transactionType
        ,final float amount
        ,final String bankCurrencyType
        ,final String bankOperationType
        ,final String bankDocumentReference
        ,final String bankToName
        ,final String bankToAccountNumber
        ,final String bankToAccountType
        ,final String bnkFromName
        ,final String bankFromAccountNumber
        ,final String bankFromAccountType
    ) throws CantCreateReceiveOfflineBankTransferException;

    void updateStatusReceiveOfflineBankTransfer(final UUID bankTransactionId) throws CantUpdateStatusReceiveOfflineBankTransferException;
    
}
