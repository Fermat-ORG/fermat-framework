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
         final String publicKeyCustomer
        ,final String publicKeyBroker
        ,final Float amount
        ,final String bankCurrencyType
        ,final String bankName
        ,final String bankAccountNumber
        ,final String bankAccountType
        ,final String bankDocumentReference
    ) throws CantCreateReceiveOfflineBankTransferException;

    void updateStatusReceiveOfflineBankTransfer(final UUID bankTransactionId) throws CantUpdateStatusReceiveOfflineBankTransferException;
    
}
