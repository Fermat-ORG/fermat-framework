package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.exceptions.CantCreateMakeOfflineBankTransferException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.exceptions.CantGetMakeOfflineBankTransferException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.make_offline_bank_transfer.exceptions.CantUpdateStatusMakeOfflineBankTransferException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 25,09,15.
 */
public interface MakeOfflineBankTransferManager {

    List<MakeOfflineBankTransfer> getAllMakeOfflineBankTransferFromCurrentDeviceUser() throws CantGetMakeOfflineBankTransferException;

    MakeOfflineBankTransfer createMakeOfflineBankTransfer(
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
    ) throws CantCreateMakeOfflineBankTransferException;

    void updateStatusMakeOfflineBankTransfer(final UUID bankTransactionId) throws CantUpdateStatusMakeOfflineBankTransferException;
}
